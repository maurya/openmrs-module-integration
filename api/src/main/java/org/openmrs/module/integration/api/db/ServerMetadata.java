package org.openmrs.module.integration.api.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.openmrs.api.APIException;
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
import org.openmrs.module.integration.OrgUnitDisplay;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.integration.api.db.DhisMetadataUtils.ContentType;
import org.openmrs.module.integration.api.jaxb.CategoriesType;
import org.openmrs.module.integration.api.jaxb.CategoryCombosType;
import org.openmrs.module.integration.api.jaxb.OrgUnitType;
import org.openmrs.module.integration.api.jaxb.ReportTemplates;
import org.openmrs.module.integration.api.jaxb.MetaData;
//import org.openmrs.module.integration.api.jaxb.OrgUnitType;
import org.openmrs.module.integration.api.jaxb.ObjectFactory;
import org.openmrs.util.OpenmrsUtil;

/**
 * This object holds methods that require unmarshalled DHIS xml.
 * It should be created when this functionality is needed and then destroyed.
 * 
 */
public class ServerMetadata {

	private static Log log = LogFactory.getLog(ServerMetadata.class);
	private final Double hash = Math.random();
	
	private String name;
	private IntegrationServer is;
	private ReportTemplates master;
	private MetaData opts;
	private MetaData cats;
	private MetaData orgs;
	private DhisService ds = Context.getService(DhisService.class);

	
	/**
	 * Creates the initial DB objects for a new server by
	 * parsing xml and traversing metadata
	 *  
	 * @param name the name of the server to be built
	 */
	public void buildDBObjects(String name) throws IntegrationException {
		log.info("In buildObjects");
		this.name=name;
		is=ds.getIntegrationServerByName(name);
		if (is==null) {
			is=new IntegrationServer();
			is.setServerName(name);
			ds.saveIntegrationServer(is);
		}
 
		try {
			master = DhisMetadataUtils.UnmarshalMaster("New", name);
			cats = DhisMetadataUtils.UnmarshalMetaData(ContentType.CATS,"New", name);
			opts = DhisMetadataUtils.UnmarshalMetaData(ContentType.OPTS,"New", name);
		} catch (IntegrationException ie) {
			throw ie;
		} catch (Exception e) {
			throw new IntegrationException(e.getMessage(),e);
		}
		
		String result = "";
		
		processOpts();
		processCats();
		processMaster();
		
		return;
	}

	private void processOpts() {
		for (CategoriesType.Category xcat : getOpts()) {
			OptionSet ops = (OptionSet) ds.getExistingByUid(OptionSet.class, xcat.getId(), is);
			if (ops==null) {
				ops = new OptionSet(xcat.getName(),"",xcat.getId());
				ops.setIntegrationServer(is);
				ops = ds.saveOptionSet(ops);
			}
			for (CategoriesType.Category.CategoryOptions.CategoryOption xco : xcat.getCategoryOptions().getCategoryOption()) {
				Option opv = (Option) ds.getExistingByUid(Option.class, xco.getId(), is);
				if (opv==null) {
					opv = new Option(xco.getName(),"",xco.getId());
					opv.setIntegrationServer(is);
					opv.setCohortdefUuid(ds.getUndefinedCohortDefinition().getUuid());
					opv = ds.saveOption(opv);
				}
				ops.getOptions().add(opv);
				opv.getOptionSets().add(ops);
				opv=ds.saveOption(opv);
			}
			ops=ds.saveOptionSet(ops);
		}
		return;
	}
	
	private void processCats() {
		
	// step through the category options
		for (CategoryCombosType.CategoryOptionCombo xcoc : getCats()) {
			
	// build the category combo if necessary
			CategoryCombosType.CategoryOptionCombo.CategoryCombo xcc = xcoc.getCategoryCombo();
			CategoryCombo cc = (CategoryCombo) ds.getExistingByUid(CategoryCombo.class, xcc.getId(), is);
			if (cc==null) {
				cc = new CategoryCombo(xcc.getName(),"",xcc.getId());
				cc.setIntegrationServer(is);
				cc = ds.saveCategoryCombo(cc);
			}
			
	// build the category option if necessary
			CategoryOption co = (CategoryOption) ds.getExistingByUid(CategoryOption.class, xcoc.getId(), is);
			if (co==null) {
				co = new CategoryOption(xcoc.getName(),"",xcoc.getId());
				co.setIntegrationServer(is);
				co = ds.saveCategoryOption(co);
			}
			
	// add the category combo and category option to each other's collections
			cc.getCategoryOptions().add(co);
			co.getCategoryCombos().add(cc);
			
	// add the options and category options to each other's collections
			for (CategoryCombosType.CategoryOptionCombo.CategoryOptions.CategoryOption xov : xcoc.getCategoryOptions().getCategoryOption()) {
				Option ov = ds.getOptionByUid(xov.getId(), is);
				if (ov!=null) {
					co.getOptions().add(ov);
					ov.getCategoryOptions().add(co);
					ov = ds.saveOption(ov);
				}
			}

	// find the option sets for the category combo if necessary
			if (cc.getOptionSets().size()==0) {
				Set<OptionSet> oss = null;
				for (CategoryCombosType.CategoryOptionCombo.CategoryOptions.CategoryOption xov : xcoc.getCategoryOptions().getCategoryOption()) {
					Option ov = ds.getOptionByUid(xov.getId(), is);
					if (ov!=null) {
						if (oss==null) {
							oss = new HashSet<OptionSet>(); 
							oss.addAll(ov.getOptionSets());
						} else {
							oss.retainAll(ov.getOptionSets());
						}
					}
				}
				cc.getOptionSets().addAll(oss);
			}
			
	// save the updated category combo and category option
			ds.saveCategoryCombo(cc);
			ds.saveCategoryOption(co);
		}
		
		return;
	}
				
	private void processMaster() {
	// process data elements
			for (ReportTemplates.DataElements.DataElement xde : getDataElements()) {
				DataElement de = (DataElement) ds.getExistingByUid(DataElement.class, xde.getUid(), is);
				if (de==null) {
					de = new DataElement(xde.getName(),xde.getCode(),xde.getUid());
					de.setIntegrationServer(is);
					de.setCohortDefinitionUuid(ds.getUndefinedCohortDefinition().getUuid());
					de = ds.saveDataElement(de);
				}
			}

	// add codes to disaggregations
			for (ReportTemplates.Disaggregations.Disaggregation xd : getMaster().getDisaggregations().getDisaggregation()) {
				CategoryOption co = ds.getCategoryOptionByUid(xd.getUid(), is);
				if (co != null) {
					co.setCode(xd.getCode());
					ds.saveCategoryOption(co);
				}
			}
			
	// process report definitions
			for (ReportTemplates.ReportTemplate xrt : getReportTemplates()) {
				ReportTemplate rt = (ReportTemplate) ds.getExistingByUid(ReportTemplate.class, xrt.getUid(), is);
				if (rt==null) {
					rt = new ReportTemplate(xrt.getName(),xrt.getCode(),xrt.getUid());
					rt.setIntegrationServer(is);
					rt.setFrequency(xrt.getPeriodType());
					rt = ds.saveReportTemplate(rt);
				}
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
						dv=ds.saveDataValueTemplate(dv);
						rt.getDataValueTemplates().add(dv);
						if (de.getCategoryCombo() == null) {
							CategoryOption co = ds.getCategoryOptionByUid(xdv.getDisaggregation(),is);
							if (co!=null) {
								Iterator<CategoryCombo> it = co.getCategoryCombos().iterator();
								CategoryCombo cb=it.next();
								if (cb != null) {
									de.setCategoryCombo(cb);
								}
							}
						}
					}
				}
			}
			return;
	}
	
/****************
// process options/option sets
		
		for (CategoriesType.Category xcat : getOpts()) {
			OptionSet ops = new OptionSet(xcat.getName(),"",xcat.getId());
			ops.setIntegrationServer(is);
			OptionSet ops2 = ds.getOptionSetByUid(ops.getUid(),is);
			if (ops2==null) {
				ops=ds.saveOptionSet(ops);
			} else {
				ops=ops2;
			}
			for (CategoriesType.Category.CategoryOptions.CategoryOption xco : xcat.getCategoryOptions().getCategoryOption()) {
				Option opv = new Option(xco.getName(),"",xco.getId());
				opv.setIntegrationServer(is);
				Option opv2 = ds.getOptionByUid(ops.getUid(),is);
				if (opv2==null) {
					opv=ds.saveOption(opv);
				} else {
					opv=opv2;
				}
				ops.getOptions().add(opv);
			}	
			ds.saveOptionSet(ops);
		}
		int nop=ds.getOptionSetsByServer(is).size();
		int nov=ds.getOptionsByServer(is).size();
// process categorycombos/categoryoptions
		
		for (CategoryCombosType.CategoryOptionCombo xcco : getCats()) {
			
// find or create the category combo
		
			CategoryCombo ccb = ds.getCategoryComboByUid(xcco.getCategoryCombo().getId(),is);
			if (ccb==null) {
				ccb = new CategoryCombo(xcco.getCategoryCombo().getName(),"",xcco.getCategoryCombo().getId());
				ccb.setIntegrationServer(is);
				ccb=ds.saveCategoryCombo(ccb);
			}

// create the category option and add it to the category combo's collection
		
			CategoryOption cco = new CategoryOption(xcco.getName(),"",xcco.getId());
			cco.setIntegrationServer(is);
			CategoryOption cco2 = ds.getCategoryOptionByUid(cco.getUid(),is);
			if (cco2==null) {
				cco=ds.saveCategoryOption(cco);
			} else {
				cco=cco2;
			}
			ccb.getCategoryOptions().add(cco);

// for each optionSet/option, add the option to the catOption collection and the option set to the cat combo collection if needed
		
			for (CategoryCombosType.CategoryOptionCombo.CategoryOptions.CategoryOption xopv : xcco.getCategoryOptions().getCategoryOption()) {
				Option opv = null;
				List<Option> lopv = ds.getOptionsByServer(is);
				for (Option v : lopv)
					if (v.getId().equals(xopv.getId())) {
						opv = v;
						break;
					} else if (v.getName().equals(xopv.getName())) {
						opv = v;
						break;
					}
//				if (ds.getOptionByUid(xopv.getId(),is)!=null) {
//					opv=ds.getOptionByUid(xopv.getId(), is);
//				}
					if (opv != null) {
						Set<Option> sopv = cco.getOptions();
						if (sopv!=null) { 
							cco.getOptions().add(opv);
						} else {
							nop=nop;
						}
					if (ccb.getOptionSets().size() < (cco.getOptions().size())){
						Iterator<OptionSet> it = opv.getOptionSets().iterator();
						if (it.hasNext()) {
							OptionSet ops = it.next();
							ccb.getOptionSets().add(ops);
						}
					}
				}
			}
		}
		nop=ds.getCategoryComboByServer(is).size();
		nov=ds.getCategoryOptionByServer(is).size();
		
// process data elements
		for (ReportTemplates.DataElements.DataElement xde : getDataElements()) {
			DataElement de = new DataElement(xde.getName(),xde.getCode(),xde.getUid());
			de.setIntegrationServer(is);
			DataElement de2 = ds.getDataElementByUid(de.getUid(),is);
			if (de2==null) {
				de=ds.saveDataElement(de);
			} else {
				de=de2;
			}
		}

// add codes to disaggregations
		for (ReportTemplates.Disaggregations.Disaggregation xd : getMaster().getDisaggregations().getDisaggregation()) {
			CategoryOption co = ds.getCategoryOptionByUid(xd.getUid(), is);
			if (co != null) {
				co.setCode(xd.getCode());
				ds.saveCategoryOption(co);
			}
		}
		
// process report definitions
		for (ReportTemplates.ReportTemplate xrt : getReportTemplates()) {
			ReportTemplate rt = new ReportTemplate(xrt.getName(),xrt.getCode(),xrt.getUid());
			rt.setFrequency(xrt.getPeriodType());
			rt.setIntegrationServer(is);
			rt=ds.saveReportTemplate(rt);
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
					dv=ds.saveDataValueTemplate(dv);
					rt.getDataValueTemplates().add(dv);
					if (de.getCategoryCombo() == null) {
						CategoryOption co = ds.getCategoryOptionByUid(xdv.getDisaggregation(),is);
						if (co!=null) {
							Iterator<CategoryCombo> it = co.getCategoryCombos().iterator();
							CategoryCombo cb=it.next();
							if (cb != null) {
								de.setCategoryCombo(cb);
							}
						}
					}
				}
			}
		}
	}
**********************/

	// basic object methods

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return hash.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return false;
	}

	// getters for the underlying components -- no setters
	
	public IntegrationServer getServer() {
		return is;
	}

	public String getName() {
		return name;
	}
	
	public ReportTemplates getMaster() {
		return master;
	}

	public List<ReportTemplates.DataElements.DataElement> getDataElements() {
		if (master==null) {
			return null;
		}
		if (master.getDataElements()==null) {
			return null;
		}
		return master.getDataElements().getDataElement();
	}

	public List<ReportTemplates.Disaggregations.Disaggregation> getDisaggregations() {
		if (master==null) {
			return null;
		}
		if (master.getDisaggregations()==null) {
			return null;
		}
		return master.getDisaggregations().getDisaggregation();
	}

	public List<ReportTemplates.ReportTemplate> getReportTemplates() {
		if (master==null) {
			return null;
		}
		return master.getReportTemplate();
	}

	public List<CategoriesType.Category> getOpts() {
		if (opts==null) {
			return null;
		}
		if (opts.getCategories()==null) {
			return null;
		}
		return opts.getCategories().getCategory();
	}

	public List<CategoryCombosType.CategoryOptionCombo> getCats() {
		if (cats==null) {
			return null;
		}
		if (cats.getCategoryOptionCombos()==null) {
			return null;
		}
		return cats.getCategoryOptionCombos().getCategoryOptionCombo();
	}
	
	public List<OrgUnitType.OrganisationUnit> getOrgs() {
		if (orgs==null) {
			return null;
		}
		if (orgs.getOrganisationUnits()==null) {
			return null;
		}
		return orgs.getOrganisationUnits().getOrganisationUnit();
	}
	
	/**
	 * 	This method prepares the org unit display.
	 *  The whole process to do so:
	 *      ServerMetadata sm = new ServerMetadata(...) // or use existing
	 *      sm.getOrgUnits(); // if not already done
	 *      sm.prepareOrgUnitDisplay(); 
	 *      SortedSet ss = OrgUnitDisplay.getAllHierarchical();
	 */
	public void prepareOrgUnitDisplay() {
		OrgUnitDisplay.Reset();
		if (orgs==null) return;
		if (orgs.getOrganisationUnits()==null) return;
		for (OrgUnitType.OrganisationUnit org : orgs.getOrganisationUnits().getOrganisationUnit()) {
			OrgUnitDisplay d = new OrgUnitDisplay(org.getName(),org.getCode(),org.getId());
			d.setLevel(org.getLevel().intValue());
			d.setServer(this.getName());
			if (org.getParent()!=null) {
				d.setParent(org.getParent().getName(), org.getParent().getCode(), org.getParent().getId(), d.getUid());
			}
		}
	}

}
