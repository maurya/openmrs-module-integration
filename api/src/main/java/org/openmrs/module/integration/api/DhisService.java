package org.openmrs.module.integration.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.metadata.ClassMetadata;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.OrgUnit;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.springframework.transaction.annotation.Transactional;

public interface DhisService extends OpenmrsService {
	
	//integration methods
	public IntegrationServer getIntegrationServerById(Integer id);
	
	public IntegrationServer getIntegrationServerByUuid(String uuid);
	
	public IntegrationServer getIntegrationServerByName(String serverName);
	
	public IntegrationServer getIntegrationServerByUrl(String url);
	
	public List<IntegrationServer> getAllIntegrationServers();
	
	public IntegrationServer saveIntegrationServer(IntegrationServer IntegrationServer);
	
	public void deleteIntegrationServer(IntegrationServer IntegrationServer);
	
	//report template methods
		public ReportTemplate getReportTemplateById(Integer id);
			
		public ReportTemplate getReportTemplateByUuid(String uuid);
			
		public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer);
		
		public List<ReportTemplate> getAllReportTemplates();
			
		public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate);
			
		public void deleteReportTemplate(ReportTemplate ReportTemplate);	
	
		//org Unit methods
		public OrgUnit getOrgUnitById(Integer id);
			
		public OrgUnit getOrgUnitByUuid(String uuid);
			
		public OrgUnit getOrgUnitByUid(String uuid, IntegrationServer is);

		public List<OrgUnit> getOrgUnitByServer(IntegrationServer integrationServer);
			
		public OrgUnit saveOrgUnit(OrgUnit OrgUnit);
			
		public void deleteOrgUnit(OrgUnit OrgUnit);	
	
		
		
		//data element methods
		public DataElement getDataElementById(Integer id);
			
		public DataElement getDataElementByUuid(String uuid);
			
		public DataElement getDataElementByUid(String uuid, IntegrationServer is);
			
		public DataElement getDataElementByCode(String code, IntegrationServer is);
			
		public List<DataElement> getDataElementsByServer(IntegrationServer integrationServer);
				
		public DataElement saveDataElement(DataElement DataElement);
			
		public void deleteDataElement(DataElement DataElement);	

			
			//Data value template methods
		public DataValueTemplate getDataValueTemplateById(Integer id);
			
		public DataValueTemplate getDataValueTemplateByUuid(String uuid);
			
		public List<DataValueTemplate> getDataValueTemplateByReportTemplate(ReportTemplate ReportTemplate);
			
		public List<DataValueTemplate> getDataValueTemplateByDataElement(DataElement DataElement);

		public List<DataValueTemplate> getDataValueTemplateByCategoryOption(CategoryOption CategoryOption);

		public DataValueTemplate saveDataValueTemplate(DataValueTemplate DataValueTemplate);
			
		public void deleteDataValueTemplate(DataValueTemplate DataValueTemplate);	

		//category combo methods
		public CategoryCombo getCategoryComboById(Integer id);
					
		public CategoryCombo getCategoryComboByUuid(String uuid);
			
		public CategoryCombo getCategoryComboByUid(String uid, IntegrationServer is);
			
		public List<CategoryCombo> getCategoryComboByServer(IntegrationServer integrationServer);
			
		public CategoryCombo saveCategoryCombo(CategoryCombo CategoryCombo);
			
		public void deleteCategoryCombo(CategoryCombo CategoryCombo);	
				
		
		
		//category option methods
		public CategoryOption getCategoryOptionById(Integer id);
			
		public CategoryOption getCategoryOptionByUuid(String uuid);
			
		public CategoryOption getCategoryOptionByUid(String uid, IntegrationServer is);
			
		public List<CategoryOption> getCategoryOptionByServer(IntegrationServer integrationServer);
			
		public CategoryOption saveCategoryOption(CategoryOption CategoryOption);
			
		public void deleteCategoryOption(CategoryOption CategoryOption);	
		
		
		//Option Set 
		public OptionSet getOptionSetById(Integer id);
			
		public OptionSet getOptionSetByUuid(String uuid);
			
		public List<OptionSet> getOptionSetsByServer(IntegrationServer integrationServer);
			
		public OptionSet saveOptionSet(OptionSet OptionSet);
			
		public void deleteOptionSet(OptionSet OptionSet);

		
	//Option 
	public Option getOptionById(Integer id);
		
	public Option getOptionByUuid(String uuid);
		
	public Option getOptionByUid(String uid, IntegrationServer is);
		
	public List<Option> getOptionsByServer(IntegrationServer integrationServer);
		
	public Option saveOption(Option Option);
		
	public void deleteOption(Option Option);
	
	
	
	//misc methods
	
	public Map<DataElement,List<CategoryOption>> getDataElementToCategoryOptionDictionaryByReportTemplate(ReportTemplate ReportTemplate);
	
	public Set<Option> getOptionToCategoryOptionDictionaryByReportTemplate(ReportTemplate ReportTemplate);
	
	public Map<String,ClassMetadata> getHibernateClassMetadata();
	
	public CohortDefinition getAllPatients();
	
	public Map<DataElement, CategoryCombo> getDataElementToCategoryComboDictionaryByReportTemplate(
			ReportTemplate ReportTemplate);
	
	public Set<OptionSet> getOptionSetsByReportTemplate(
			ReportTemplate ReportTemplate);
	
	public CohortDefinition getUndefinedCohortDefinition();
	
	public void commit();
		
}
