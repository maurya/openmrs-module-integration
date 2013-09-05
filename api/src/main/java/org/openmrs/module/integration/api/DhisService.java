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
import org.springframework.transaction.annotation.Transactional;

public interface DhisService extends OpenmrsService {
	
	//integration methods
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerById(Integer id);
	
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUuid(String uuid);
	
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByName(String serverName);
	
	@Transactional(readOnly = true)
	public IntegrationServer getIntegrationServerByUrl(String url);
	
	@Transactional(readOnly = true)
	public List<IntegrationServer> getAllIntegrationServers();
	
	@Transactional
	public IntegrationServer saveIntegrationServer(IntegrationServer IntegrationServer);
	
	@Transactional
	public void deleteIntegrationServer(IntegrationServer IntegrationServer);
	
	//report template methods
		@Transactional(readOnly = true)
		public ReportTemplate getReportTemplateById(Integer id);
			
		@Transactional(readOnly = true)
		public ReportTemplate getReportTemplateByUuid(String uuid);
		
		@Transactional(readOnly = true)
		public OrgUnit getOrgUnitByUid(String uuid, IntegrationServer is);
			
		@Transactional(readOnly = true)
		public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer);
		
		
		@Transactional(readOnly = true)
		public List<ReportTemplate> getAllReportTemplates();
			
		@Transactional
		public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate);
			
		@Transactional
		public void deleteReportTemplate(ReportTemplate ReportTemplate);	
	
		//org Unit methods
		@Transactional(readOnly = true)
		public OrgUnit getOrgUnitById(Integer id);
			
		@Transactional(readOnly = true)
		public OrgUnit getOrgUnitByUuid(String uuid);
			
		@Transactional(readOnly = true)
		public List<OrgUnit> getOrgUnitByServer(IntegrationServer integrationServer);
			
		@Transactional
		public OrgUnit saveOrgUnit(OrgUnit OrgUnit);
			
		@Transactional
		public void deleteOrgUnit(OrgUnit OrgUnit);	
	
		
		
		//data element methods
		@Transactional(readOnly = true)
		public DataElement getDataElementById(Integer id);
			
		@Transactional(readOnly = true)
		public DataElement getDataElementByUuid(String uuid);
		
		@Transactional(readOnly = true)
		public DataElement getDataElementByUid(String uuid, IntegrationServer is);
			
		@Transactional(readOnly = true)
		public List<DataElement> getDataElementsByServer(IntegrationServer integrationServer);
				
		@Transactional
		public DataElement saveDataElement(DataElement DataElement);
			
		@Transactional
		public void deleteDataElement(DataElement DataElement);	

			
			//Data value template methods
		@Transactional(readOnly = true)
		public DataValueTemplate getDataValueTemplateById(Integer id);
			
		@Transactional(readOnly = true)
		public DataValueTemplate getDataValueTemplateByUuid(String uuid);
			
		@Transactional(readOnly = true)
		public List<DataValueTemplate> getDataValueTemplateByReportTemplate(ReportTemplate ReportTemplate);
			
		@Transactional(readOnly = true)
		public List<DataValueTemplate> getDataValueTemplateByDataElement(DataElement DataElement);

		@Transactional(readOnly = true)
		public List<DataValueTemplate> getDataValueTemplateByCategoryOption(CategoryOption CategoryOption);

		@Transactional
		public DataValueTemplate saveDataValueTemplate(DataValueTemplate DataValueTemplate);
			
		@Transactional
		public void deleteDataValueTemplate(DataValueTemplate DataValueTemplate);	

		//category combo methods
		@Transactional(readOnly = true)
		public CategoryCombo getCategoryComboById(Integer id);
					
		@Transactional(readOnly = true)
		public CategoryCombo getCategoryComboByUuid(String uuid);
		
		@Transactional(readOnly = true)
		public CategoryCombo getCategoryComboByUid(String uid, IntegrationServer is);
			
		@Transactional(readOnly = true)
		public List<CategoryCombo> getCategoryComboByServer(IntegrationServer integrationServer);
			
		@Transactional
		public CategoryCombo saveCategoryCombo(CategoryCombo CategoryCombo);
			
		@Transactional
		public void deleteCategoryCombo(CategoryCombo CategoryCombo);	
				
		
		
		//category option methods
		@Transactional(readOnly = true)
		public CategoryOption getCategoryOptionById(Integer id);
			
		@Transactional(readOnly = true)
		public CategoryOption getCategoryOptionByUuid(String uuid);
		
		@Transactional(readOnly = true)
		public CategoryOption getCategoryOptionByUid(String uid, IntegrationServer is);
			
		@Transactional(readOnly = true)
		public List<CategoryOption> getCategoryOptionByServer(IntegrationServer integrationServer);
			
		@Transactional
		public CategoryOption saveCategoryOption(CategoryOption CategoryOption);
			
		@Transactional
		public void deleteCategoryOption(CategoryOption CategoryOption);	
		
		
		//Option Set 
		@Transactional(readOnly = true)
		public OptionSet getOptionSetById(Integer id);
			
		@Transactional(readOnly = true)
		public OptionSet getOptionSetByUuid(String uuid);
			
		@Transactional(readOnly = true)
		public List<OptionSet> getOptionSetsByServer(IntegrationServer integrationServer);
			
		@Transactional
		public OptionSet saveOptionSet(OptionSet OptionSet);
			
		@Transactional
		public void deleteOptionSet(OptionSet OptionSet);
		
	//Option 
	@Transactional(readOnly = true)
	public Option getOptionById(Integer id);
		
	@Transactional(readOnly = true)
	public Option getOptionByUuid(String uuid);
	
	@Transactional(readOnly = true)
	public Option getOptionByUid(String uid, IntegrationServer is);
		
	@Transactional(readOnly = true)
	public List<Option> getOptionsByServer(IntegrationServer integrationServer);
		
	@Transactional
	public Option saveOption(Option Option);
		
	@Transactional
	public void deleteOption(Option Option);
	
	
	
	//misc methods

	public Map<String,ClassMetadata> getHibernateClassMetadata();
	
	public Map<DataElement,CategoryCombo> getDataElementToCategoryComboDictionaryByReportTemplate(ReportTemplate ReportTemplate);
	
	public Set<OptionSet> getOptionSetsByReportTemplate(ReportTemplate ReportTemplate);
	
		
}
