package org.openmrs.module.integration.api;

import java.util.List;
import java.util.Map;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.CategoryOptionToOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
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
	
	@Transactional(readOnly = true)
	public void deleteIntegrationServer(IntegrationServer IntegrationServer);
	

	//Option Set 
	@Transactional(readOnly = true)
	public Option getOptionById(Integer id);
		
	@Transactional(readOnly = true)
	public Option getOptionByUuid(String uuid);
		
	@Transactional(readOnly = true)
	public List<Option> getOptionsByServer(IntegrationServer integrationServer);
		
	@Transactional
	public Option saveOption(Option Option);
		
	@Transactional
	public void deleteOption(Option Option);
	
	
	//category combo methods
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionById(Integer id);
		
	@Transactional(readOnly = true)
	public CategoryOption getCategoryOptionByUuid(String uuid);
		
	@Transactional(readOnly = true)
	public List<CategoryOption> getCategoryOptionByServer(IntegrationServer integrationServer);
		
	@Transactional
	public CategoryOption saveCategoryOption(CategoryOption CategoryOption);
		
	@Transactional
	public void deleteCategoryOption(CategoryOption CategoryOption);	
	
	//Category combo to option set methods
	@Transactional(readOnly = true)
	public List<CategoryOptionToOption> getCategoryOptionToOptionByCategoryOption(CategoryOption CategoryOption);
		
	@Transactional(readOnly = true)
	public List<CategoryOptionToOption> getCategoryOptionToOptionByOption(Option Option);
		
	@Transactional
	public CategoryOptionToOption saveCategoryOptionToOption(CategoryOptionToOption CategoryOptionToOption);
		
	@Transactional
	public void deleteCategoryOptionToOption(CategoryOptionToOption CategoryOptionToOption);	
	
	//report template methods
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateById(Integer id);
		
	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplateByUuid(String uuid);
		
	@Transactional(readOnly = true)
	public List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer);
		
	@Transactional
	public ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate);
		
	@Transactional
	public void deleteReportTemplate(ReportTemplate ReportTemplate);	
		
		
		//data element methods
	@Transactional(readOnly = true)
	public DataElement getDataElementById(Integer id);
		
	@Transactional(readOnly = true)
	public DataElement getDataElementByUuid(String uuid);
		
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
	
	//miscellaneous functions
	
	@Transactional(readOnly = true)
	public Map<DataElement, List<CategoryOption>> getDataElementToCategoryOptionDictionaryByReportTemplate(
			ReportTemplate reportTemplate);


		
}
