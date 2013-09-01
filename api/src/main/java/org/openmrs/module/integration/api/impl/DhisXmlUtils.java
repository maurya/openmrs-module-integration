package org.openmrs.module.integration.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.OrgUnit;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.UndefinedCohortDefinition;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.integration.api.db.DhisDAO;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.module.integration.api.db.hibernate.HibernateDhisDAO;
import org.openmrs.module.integration.api.jaxb.MetaData;
import org.openmrs.module.integration.api.jaxb.ReportTemplates;
import org.openmrs.module.reporting.cohort.definition.AllPatientsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.util.OpenmrsUtil;

public class DhisXmlUtils {
	// Logger
	private transient Log log = LogFactory.getLog(DhisServiceImpl.class);

	// Constants
	private static String MODULE_NAME = "Integration";
	private static String[] XML_SETS = {"master","cats","opts","orgs"};
//	private MessageSourceService mss; 
	
	// Private variables
	private DhisService ds;
	private CohortDefinition undefined;
	private CohortDefinition allPatients;
	
	public DhisXmlUtils() {
//		mss = Context.getMessageSourceService();
	}
	
    /**
     * The undefined cohort is a singleton
     * @return the undefined cohort
     */
    public CohortDefinition getUndefinedCohort() {
    	if (undefined==null) {
    		CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
    		List<CohortDefinition> cd = cds.getAllDefinitions(false);
    		for (CohortDefinition d : cd) {
    			if (d.getClass().equals(UndefinedCohortDefinition.class)) {
    				undefined=d;
    				break;
    			}
    		}
    		if (undefined==null) {
    			this.undefined = new UndefinedCohortDefinition();
    			cds.saveDefinition(this.undefined);
    		}
    	}
    	return undefined;
    }
    
    /**
     * The allPatients cohort is not unique, it will be created if needed
     * @return the AllPatients cohort
     */
    public CohortDefinition getAllPatients() {
    	if (allPatients==null) {
    		CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
    		List<CohortDefinition> cd = cds.getAllDefinitions(false);
    		for (CohortDefinition d : cd) {
    			if (d.getClass().equals(AllPatientsCohortDefinition.class)) {
    				allPatients=d;
    				break;
    			}
    		}
    		if (allPatients==null) {
    			this.allPatients = new AllPatientsCohortDefinition();
    			this.allPatients.setName("All Patients");
    			cds.saveDefinition(this.allPatients);
    		}
    	}
    	return allPatients;
    }
    
	/**
	 * Creates a new server via the DHIS API.  Gets the xml into New and Current dirs.
	 * Creates a ServerMetadata representing the xml and uses it to build DB ojects.
	 * @param is the server to create
	 * @return string with error message or empty on success
	 */
	public String createNewServer(IntegrationServer is) {
		ds=Context.getService(DhisService.class);
		String result = "";
		
// save the server just in case
		try {
			ds.saveIntegrationServer(is);
		} catch (Exception e) {
			result = e.getLocalizedMessage();
		}
		if (! "".equals(result))
			return result;

// get all 4 object sets
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata(is);
			sm.getOrgUnits();
		} catch (IntegrationException e) {
			result = e.getLocalizedMessage();
		}
		if (! "".equals(result))
			return result;

// copy the files from New to Current
		final StringBuilder sb = new StringBuilder();
		sb.append(MODULE_NAME);
		sb.append(File.separatorChar);
		sb.append(is.getServerName());
		sb.append(File.separatorChar);
		File folderNew = OpenmrsUtil
				.getDirectoryInApplicationDataDirectory(sb.toString() + "New");
		File folderCurrent = OpenmrsUtil
				.getDirectoryInApplicationDataDirectory(sb.toString() + "Current");
		for (String s : XML_SETS) {
			try {
				File of = new File(folderCurrent, s + ".xml");
				OutputStream os = new FileOutputStream(of);
				File inf = new File(folderNew, s + ".xml");
				InputStream ins = new FileInputStream(inf);
				IOUtils.copy(ins, os);
			} catch (Exception e) {
				result = e.getLocalizedMessage();
				break;
			}
		}
		if (! "".equals(result))
			return result;
		
		return buildDBObjects(sm);

	}
	
	/**
	 * Creates a new server from resources.  Primary use is testing
	 * Creates a ServerMetadata representing the xml and uses it to build DB ojects.
	 * @param name the name of the server to create
	 * @param master the resource with the master xml
	 * @param cats the resource with the category combo xml
	 * @param opts the resource with the option set xml
	 * @param orgs the resource with the org unit xml
	 * @return string with error message or empty on success
	 */
	public String createNewServer(String name, String master, String cats, String opts, String orgs) {
		String result = "";
		
		ds=Context.getService(DhisService.class);
		ServerMetadata sm = new ServerMetadata();
		
		try {
			sm.getServerMetadata(master, cats, opts);
			sm.getOrgUnits(orgs);
		} catch (IntegrationException e) {
			result = e.getLocalizedMessage();
			return result;
		}
		
		IntegrationServer is = sm.getServer();
		is.setServerName(name);
		is.setUserName("username");
		is.setPassword("password");
		is.setUrl("url");
		is.setEmailorurl("neither");
		is.setTransportType("transport");
		try {
			ds.saveIntegrationServer(is);
		} catch (Exception e) {
			result=e.getLocalizedMessage();
			return result;
		}

		result = buildDBObjects(sm);
		
		return result;
	}
	
	/**
	 * Traverses ServerMetadata to build DB objects.
	 * @param sm the ServerMetadata from which to build the DB object
	 * @return string with error message or empty on success
	 */
	public String buildDBObjects(ServerMetadata sm) {
		
		String result = "";
		IntegrationServer is = sm.getServer();

// process options/option sets
		for (MetaData.Categories.Category xcat : sm.getOpts()) {
			OptionSet ops = new OptionSet();
			ops.setName(xcat.getName());
			ops.setUid(xcat.getId());
			for (MetaData.Categories.Category.CategoryOptions.CategoryOption xco : xcat.getCategoryOptions().getCategoryOption()) {
				Option opv = new Option();
				opv.setName(xco.getName());
				opv.setUid(xco.getId());
				ds.saveOption(opv);
				ops.getOptions().add(opv);
			}	
			ds.saveOptionSet(ops);
		}

// process categorycombos/categoryoptions
		for (MetaData.CategoryOptionCombos.CategoryOptionCombo xcco : sm.getCats()) {
// find or create the category combo
			CategoryCombo ccb = ds.getCategoryComboByUid(xcco.getCategoryCombo().getId(),is);
			if (ccb==null) {
				ccb = new CategoryCombo();
				ccb.setName(xcco.getCategoryCombo().getName());
				ccb.setUid(xcco.getCategoryCombo().getId());
				ds.saveCategoryCombo(ccb);
			}

// create the category option and add it to the category combo's collection
			CategoryOption cco = new CategoryOption();
			cco.setName(xcco.getName());
			cco.setUid(xcco.getId());
			ds.saveCategoryOption(cco);
			ccb.getCategoryOptions().add(cco);

// for each optionSet/option, add the option to the catOption collection and the option set to the cat combo collection if needed
			for (MetaData.CategoryOptionCombos.CategoryOptionCombo.CategoryOptions.CategoryOption xopv : xcco.getCategoryOptions().getCategoryOption()) {
				Option opv = ds.getOptionByUid(xopv.getId(),is);
				if (opv != null) {
					cco.getOptions().add(opv);
					if (ccb.getOptionSets().size() < (cco.getOptions().size())){
						Iterator<OptionSet> it = opv.getOptionSets().iterator();
						OptionSet ops = it.next();
						if (ops != null) {
							ccb.getOptionSets().add(ops);
						}
					}
				}
			}
		}
		
// process data elements
		for (ReportTemplates.DataElements.DataElement xde : sm.getDataElements()) {
			DataElement de = new DataElement();
			de.setDataElementName(xde.getName());
			de.setDataElementCode(xde.getCode());
			if (de.getDataElementCode()==null) {
				de.setDataElementCode(xde.getUid());
			}
			de.setDataElementUid(xde.getUid());
			de.setIntegrationServer(is);
			ds.saveDataElement(de);
		}

// add codes to disaggregations
		for (ReportTemplates.Disaggregations.Disaggregation xd : sm.getMaster().getDisaggregations().getDisaggregation()) {
			CategoryOption co = ds.getCategoryOptionByUid(xd.getUid(), is);
			if (co != null) {
				co.setCode(xd.getCode());
				ds.saveCategoryOption(co);
			}
		}
		
// process report definitions
		for (ReportTemplates.ReportTemplate xrt : sm.getReportTemplates()) {
			ReportTemplate rt = new ReportTemplate();
			rt.setReportTemplateName(xrt.getName());
			rt.setReportTemplateCode(xrt.getCode());
			rt.setUid(xrt.getUid());
			rt.setFrequency(xrt.getPeriodType());
			rt.setIntegrationServer(is);
			ds.saveReportTemplate(rt);
			for (ReportTemplates.ReportTemplate.DataValueTemplates.DataValueTemplate xdv : xrt.getDataValueTemplates().getDataValueTemplate()) {
				DataValueTemplate dv = new DataValueTemplate();
				dv.setReportTemplate(rt);
				dv.setIntegrationServer(is);
				DataElement de = ds.getDataElementByCode(xdv.getDataElement(),is);
				if (de==null) {
					de = ds.getDataElementByUid(xdv.getDataElement(),is);
				}
				if (de!=null) {
					dv.setDataElement(de);
					dv.setCategoryOption(ds.getCategoryOptionByUid(xdv.getDisaggregation(),is));
					ds.saveDataValueTemplate(dv);
					rt.getDataValueTemplates().add(dv);
					if (de.getCategoryCombo() == null) {
						Iterator<CategoryCombo> it = ds.getCategoryOptionByUid(xdv.getDisaggregation(),is).getCategoryCombos().iterator();
						CategoryCombo cb=it.next();
						if (cb != null) {
							de.setCategoryCombo(cb);
						}
					}
				}
			}
		}
		
//process org units
		if (sm.getOrgs().size() == 0) {
//			result = mss.getMessage("DhisXml.CreateNewServer.NoOrgs");
			return result;
		}
//make sure there is at least one org unit for this process
//allow for the fact the higher levels may be absent -- nOrg is total org units found
		int nOrg=0;
		byte level=0;
//work from level 1 up to make sure parents already exist -- kOrg is units found at this level
		do {
			int kOrg=0;
			level++;
			do {
				for (MetaData.OrganisationUnits.OrganisationUnit xorg : sm.getOrgs()) {
					if (xorg.getLevel()==level) {	
						kOrg++;
						OrgUnit org = new OrgUnit();
						org.setName(xorg.getName());
						org.setUid(xorg.getId());
						org.setCode(xorg.getCode());
						org.setIntegrationServer(is);
						ds.saveOrgUnit(org);
						if (xorg.getParent() != null) {
							OrgUnit parent=ds.getOrgUnitByUid(xorg.getParent().getId(),is);
							if (parent!=null) {
								parent.getChildOrgs().add(org);
								ds.saveOrgUnit(parent);
							}
						}
						ds.saveOrgUnit(org);
					}
				}
				nOrg += kOrg;
			} while (kOrg!=0);
//keep passing through all org units units a level is reached with no org units
//so long as we have processed some units
		} while (nOrg==0);  
		
		
		return result;
	}
}
