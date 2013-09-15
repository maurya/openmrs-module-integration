package org.openmrs.module.integration.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.metadata.ClassMetadata;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.OrgUnit;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.ServiceLocationCohortDefinition;
import org.openmrs.module.integration.UndefinedCohortDefinition;
import org.openmrs.module.integration.api.DhisService;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.integration.api.db.DhisDAO;
import org.openmrs.module.reporting.cohort.definition.AllPatientsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;

public class DhisServiceImpl extends BaseOpenmrsService implements DhisService {
	
		// Logger
		private transient Log log = LogFactory.getLog(DhisServiceImpl.class);
		private static final String MODULE_NAME = "Integration";
		private static final String UNDEFINED_DESC = "For use by " + MODULE_NAME;
		private static final String ALL_PATIENTS = "All Patients";
		
		// Private variables
		private DhisDAO dao;
		private CohortDefinition undefined;
		private CohortDefinition allPatients;
		private CohortDefinition serviceLocation;

		/**
		* @param dao the dao to set
		*/
		    public void setDao(DhisDAO dao) {
		this.dao = dao;
		    }
		    
		    /**
		* @return the dao
		*/
		    public DhisDAO getDao() {
		return dao;
		    }
	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerById(Integer id) {
		return dao.getIntegrationServerById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUuid(String uuid) {
		return dao.getIntegrationServerByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByName(String serverName) {
		return dao.getIntegrationServerByName(serverName);
	}

	@Override
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUrl(String url) {
		return dao.getIntegrationServerByUrl(url);
	}

	@Override
	@Transactional(readOnly = true)
	public List<IntegrationServer> getAllIntegrationServers() {
		return dao.getAllIntegrationServers();
	}

	@Override
	@Transactional
	public IntegrationServer saveIntegrationServer(
			IntegrationServer IntegrationServer) {
		return dao.saveIntegrationServer(IntegrationServer);
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteIntegrationServer(IntegrationServer IntegrationServer) {
		 dao.deleteIntegrationServer(IntegrationServer);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Option getOptionById(Integer id) {
		return dao.getOptionById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Option getOptionByUuid(String uuid) {
		return dao.getOptionByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public Option getOptionByUid(String uid, IntegrationServer is) {
		return dao.getOptionByUid(uid,is);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Option> getOptionsByServer(IntegrationServer IntegrationServer) {
		return dao.getOptionsByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public Option saveOption(Option Option) {
		return dao.saveOption(Option);
	}

	@Override
	@Transactional
	public void deleteOption(Option Option) {
		dao.deleteOption(Option);
		
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionById(Integer id) {
		return dao.getCategoryOptionById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionByUuid(String uuid) {
		return dao.getCategoryOptionByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionByUid(String uid, IntegrationServer is) {
		return dao.getCategoryOptionByUid(uid,is);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryOption> getCategoryOptionByServer(IntegrationServer IntegrationServer) {
		return dao.getCategoryOptionByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public CategoryOption saveCategoryOption(CategoryOption CategoryOption) {
		return dao.saveCategoryOption(CategoryOption);
	}

	@Override
	@Transactional
	public void deleteCategoryOption(CategoryOption CategoryOption) {
		dao.deleteCategoryOption(CategoryOption);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateById(Integer id) {
		return dao.getReportTemplateById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateByUuid(String uuid) {
		return dao.getReportTemplateByUuid(uuid);
	}


	@Override
	@Transactional(readOnly = true)
	public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer) {
		return dao.getReportTemplatesByServer(integrationServer);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ReportTemplate> getAllReportTemplates(){
		return dao.getAllReportTemplates();
	}

	@Override
	@Transactional
	public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate) {
		return dao.saveReportTemplate(ReportTemplate);
	}

	@Override
	@Transactional
	public void deleteReportTemplate(ReportTemplate ReportTemplate) {
		dao.deleteReportTemplate(ReportTemplate);
		
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementById(Integer id) {
		return dao.getDataElementById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementByUuid(String uuid) {
		return dao.getDataElementByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementByUid(String uid, IntegrationServer is) {
		return dao.getDataElementByUid(uid,is);
	}

	@Override
	@Transactional(readOnly = true)
	public DataElement getDataElementByCode(String code, IntegrationServer is) {
		return dao.getDataElementByCode(code,is);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataElement> getDataElementsByServer(IntegrationServer IntegrationServer) {
		return dao.getDataElementsByServer(IntegrationServer);
	}

	@Override
	@Transactional
	public DataElement saveDataElement(DataElement DataElement) {
		return dao.saveDataElement(DataElement);
	}

	@Override
	@Transactional
	public void deleteDataElement(DataElement DataElement) {
		dao.deleteDataElement(DataElement);
		
	}

	@Override
	@Transactional(readOnly = true)
	public DataValueTemplate getDataValueTemplateById(Integer id) {
		return dao.getDataValueTemplateById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public DataValueTemplate getDataValueTemplateByUuid(String uuid) {
		return dao.getDataValueTemplateByUuid(uuid);
				
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByReportTemplate(
			ReportTemplate ReportTemplate) {
		return dao.getDataValueTemplateByReportTemplate(ReportTemplate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByDataElement(
			DataElement DataElement) {
		return dao.getDataValueTemplateByDataElement(DataElement);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataValueTemplate> getDataValueTemplateByCategoryOption(
			CategoryOption CategoryOption) {
		return dao.getDataValueTemplateByCategoryOption(CategoryOption);
	}

	@Override
	@Transactional
	public DataValueTemplate saveDataValueTemplate(
			DataValueTemplate DataValueTemplate) {
		return dao.saveDataValueTemplate(DataValueTemplate);
	}

	@Override
	@Transactional
	public void deleteDataValueTemplate(DataValueTemplate DataValueTemplate) {
		dao.deleteDataValueTemplate(DataValueTemplate);
		
	}

	/*@Override
	@Transactional(readOnly = true)
	public Map<DataElement, List<CategoryOption>> getDataElementToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		
		DataElement de=new DataElement();
		CategoryOption co=new CategoryOption();
		List<CategoryOption> temporaryCategoryOptionList=new ArrayList<CategoryOption>();
		Map<DataElement,List<CategoryOption>> DataElementToCategoryOptionDictionary = new HashMap<DataElement, List<CategoryOption>>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		for(DataValueTemplate d:DataValueTemplateList){
			de=d.getDataElement();
			co=d.getCategoryOption();			
			temporaryCategoryOptionList=DataElementToCategoryOptionDictionary.get(de);
			if(temporaryCategoryOptionList== null){	
				temporaryCategoryOptionList=new ArrayList<CategoryOption>();
				DataElementToCategoryOptionDictionary.put(de, temporaryCategoryOptionList);
			}
			temporaryCategoryOptionList.add(co);
		}
		return DataElementToCategoryOptionDictionary;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Option> getOptionToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		Set<Option> OptionsList=new HashSet<Option>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		Set<CategoryOption> categoryOptionList = new HashSet<CategoryOption>();
		for(DataValueTemplate d:DataValueTemplateList){
			categoryOptionList.add(d.getCategoryOption());
			}
		for(CategoryOption co: categoryOptionList){
			OptionsList.addAll(co.getOptions());
			}
		return OptionsList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReportTemplate> getAllReportTemplatesMapped() {
		List<ReportTemplate> mappedReportTemplates= dao.getAllReportTemplates();
		for(ReportTemplate rt:mappedReportTemplates){
			
			if(rt.getMappedReportUuid()==null)
				mappedReportTemplates.remove(rt);
		}
		return mappedReportTemplates;
	}
*/
	@Override
	@Transactional(readOnly = true)
	public OrgUnit getOrgUnitById(Integer id) {
		return dao.getOrgUnitById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public OrgUnit getOrgUnitByUuid(String uuid) {
		return dao.getOrgUnitByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public OrgUnit getOrgUnitByUid(String uid, IntegrationServer is) {
		return dao.getOrgUnitByUid(uid, is);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrgUnit> getOrgUnitByServer(IntegrationServer integrationServer) {
		return dao.getOrgUnitByServer(integrationServer);
	}

	@Override
	@Transactional
	public OrgUnit saveOrgUnit(OrgUnit OrgUnit) {
		return dao.saveOrgUnit(OrgUnit);
	}

	@Override
	@Transactional
	public void deleteOrgUnit(OrgUnit OrgUnit) {
		dao.deleteOrgUnit(OrgUnit);
		
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryCombo getCategoryComboById(Integer id) {
		return dao.getCategoryComboById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryCombo getCategoryComboByUuid(String uuid) {
		return dao.getCategoryComboByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryCombo getCategoryComboByUid(String uid, IntegrationServer is) {
		return dao.getCategoryComboByUid(uid,is);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryCombo> getCategoryComboByServer(
			IntegrationServer integrationServer) {
		return dao.getCategoryComboByServer(integrationServer);
	}

	@Override
	@Transactional
	public CategoryCombo saveCategoryCombo(CategoryCombo CategoryCombo) {
		return dao.saveCategoryCombo(CategoryCombo);
	}

	@Override
	@Transactional
	public void deleteCategoryCombo(CategoryCombo CategoryCombo) {
		dao.deleteCategoryCombo(CategoryCombo);
		
	}

	@Override
	@Transactional(readOnly = true)
	public OptionSet getOptionSetById(Integer id) {
		return dao.getOptionSetById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public OptionSet getOptionSetByUuid(String uuid) {
		return dao.getOptionSetByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OptionSet> getOptionSetsByServer(
			IntegrationServer integrationServer) {
		return dao.getOptionSetsByServer(integrationServer);
	}

	@Override
	@Transactional
	public OptionSet saveOptionSet(OptionSet OptionSet) {
		return dao.saveOptionSet(OptionSet);
	}

	@Override
	@Transactional
	public void deleteOptionSet(OptionSet OptionSet) {
		dao.deleteOptionSet(OptionSet);
	}

	@Override
	public Map<DataElement, List<CategoryOption>> getDataElementToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		DataElement de=new DataElement();
		CategoryOption co=new CategoryOption();
		List<CategoryOption> temporaryCategoryOptionList=new ArrayList<CategoryOption>();
		Map<DataElement,List<CategoryOption>> DataElementToCategoryOptionDictionary = new HashMap<DataElement, List<CategoryOption>>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		for(DataValueTemplate d:DataValueTemplateList){
			de=d.getDataElement();
			co=d.getCategoryOption();			
			temporaryCategoryOptionList=DataElementToCategoryOptionDictionary.get(de);
			if(temporaryCategoryOptionList== null){	
				temporaryCategoryOptionList=new ArrayList<CategoryOption>();
				DataElementToCategoryOptionDictionary.put(de, temporaryCategoryOptionList);
			}
			temporaryCategoryOptionList.add(co);
		}
		return DataElementToCategoryOptionDictionary;
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<Option> getOptionToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		Set<Option> OptionsList=new HashSet<Option>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		Set<CategoryOption> categoryOptionList = new HashSet<CategoryOption>();
		for(DataValueTemplate d:DataValueTemplateList){
			categoryOptionList.add(d.getCategoryOption());
			}
		for(CategoryOption co: categoryOptionList){
			OptionsList.addAll(co.getOptions());
			}
		return OptionsList;
	}

	@Override
	public Map<String,ClassMetadata> getHibernateClassMetadata() {
		return dao.getHibernateClassMetadata();
	}
	
	@Override
	public Map<DataElement, CategoryCombo> getDataElementToCategoryComboDictionaryByReportTemplate(
			ReportTemplate ReportTemplate) {
		DataElement de=new DataElement();
		CategoryOption co=new CategoryOption();
		Map<DataElement,CategoryCombo> DataElementToCategoryComboDictionary = new HashMap<DataElement, CategoryCombo>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		Set<CategoryCombo> categoryComboList=new HashSet<CategoryCombo>();;
		for(DataValueTemplate d:DataValueTemplateList){
			de=d.getDataElement();
			co=d.getCategoryOption();
			categoryComboList=co.getCategoryCombos();
			for(CategoryCombo c:categoryComboList){
				DataElementToCategoryComboDictionary.put(de,c);
			}
		}
		return DataElementToCategoryComboDictionary;
		
	}
	
	@Override
	public Set<OptionSet> getOptionSetsByReportTemplate(
			ReportTemplate ReportTemplate) {
		Set<OptionSet> OptionSetList=new HashSet<OptionSet>();
		Set<CategoryCombo> categoryComboList=new HashSet<CategoryCombo>();
		List<DataValueTemplate> DataValueTemplateList=getDataValueTemplateByReportTemplate(ReportTemplate);
		Set<CategoryOption> categoryOptionList = new HashSet<CategoryOption>();
		for(DataValueTemplate d:DataValueTemplateList){
			categoryOptionList.add(d.getCategoryOption());
			}
		for(CategoryOption co: categoryOptionList){
			categoryComboList.addAll(co.getCategoryCombos());
			}
		for(CategoryCombo cc: categoryComboList){
			OptionSetList.addAll(cc.getOptionSets());
			}
		return OptionSetList;
	}
	
	@Override
	@Transactional(readOnly=true)
	public CohortDefinition getUndefinedCohortDefinition() {
		if (undefined==null) {
			CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
			List<CohortDefinition> cd = cds.getAllDefinitions(true);
			Boolean found = false;
			for (CohortDefinition d : cd) {
				if (d instanceof UndefinedCohortDefinition) {
					undefined =  d;
					found = true;
					break;
				}
			}

			if (!found) {
				undefined = new UndefinedCohortDefinition();
				cds.saveDefinition(undefined);
			}
		}

		return undefined;
	}
	
	@Override
	public void commit() {
		dao.commit();
	}

     /**
     * The allPatients cohort will be created if needed
     * @return the AllPatients cohort
     */
	@Override
	@Transactional(readOnly=true)
    public CohortDefinition getAllPatients() {
    	if (allPatients==null) {
			CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
			List<CohortDefinition> cd = cds.getAllDefinitions(true);
			Boolean found = false;
			for (CohortDefinition d : cd) {
				if (d instanceof AllPatientsCohortDefinition) {
					if (d.getName().equals(ALL_PATIENTS) && 
							d.getDescription().equals(UNDEFINED_DESC)) {
						allPatients = d;
						found = true;
						break;
					}
				}
			}

			if (!found) {
    			allPatients = new AllPatientsCohortDefinition();
    			allPatients.setName(ALL_PATIENTS);
    			allPatients.setDescription(UNDEFINED_DESC);
    			allPatients.setRetired(true);
    			allPatients.setDateRetired(new Date());
    			allPatients=cds.saveDefinition(allPatients);
			}
    	}
    	return allPatients;
    }

	@Override
	@Transactional(readOnly=true)
	public CohortDefinition getServiceLocationCohortDefinition() {
		if (serviceLocation==null) {
			CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
			List<CohortDefinition> cd = cds.getAllDefinitions(true);
			Boolean found = false;
			for (CohortDefinition d : cd) {
				if (d instanceof ServiceLocationCohortDefinition) {
					serviceLocation =  d;
					found = true;
					break;
				}
			}

			if (!found) {
				serviceLocation = new ServiceLocationCohortDefinition();
				cds.saveDefinition(serviceLocation);
			}
		}

		return serviceLocation;
	}
	
}
