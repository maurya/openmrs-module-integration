package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.DataValueTemplate;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;

public interface DhisDAO {
	
	//Integration Server Methods
	IntegrationServer getIntegrationServerById(Integer id);
	
	IntegrationServer getIntegrationServerByUuid(String uuid);
	
	IntegrationServer getIntegrationServerByName(String name);
	
	IntegrationServer getIntegrationServerByUrl(String url);
	
	IntegrationServer saveIntegrationServer(IntegrationServer IntegrationServer);
	
	void deleteIntegrationServer(IntegrationServer IntegrationServer);
	
	List<IntegrationServer> getAllIntegrationServers();
	
	
	//Option Set methods
	Option getOptionById(Integer id);
	
	Option getOptionByUuid(String uuid);
	
	List<Option> getOptionsByServer(IntegrationServer integrationServer);
	
	Option saveOption(Option Option);
	
	void deleteOption(Option Option);
	
	
	//category combo methods
	CategoryOption getCategoryOptionById(Integer id);
	
	CategoryOption getCategoryOptionByUuid(String uuid);
	
	List<CategoryOption> getCategoryOptionByServer(IntegrationServer integrationServer);
	
	CategoryOption saveCategoryOption(CategoryOption CategoryOption);
	
	void deleteCategoryOption(CategoryOption CategoryOption);	
		

	//report template methods
	ReportTemplate getReportTemplateById(Integer id);
	
	ReportTemplate getReportTemplateByUuid(String uuid);
	
	List<ReportTemplate> getAllReportTemplates();
	
	List<ReportTemplate> getReportTemplatesByServer(IntegrationServer integrationServer);
	
	ReportTemplate saveReportTemplate(ReportTemplate ReportTemplate);
	
	void deleteReportTemplate(ReportTemplate ReportTemplate);	
	
	
	//data element methods
	DataElement getDataElementById(Integer id);
	
	DataElement getDataElementByUuid(String uuid);
	
	List<DataElement> getDataElementsByServer(IntegrationServer integrationServer);
		
	DataElement saveDataElement(DataElement DataElement);
	
	void deleteDataElement(DataElement DataElement);	

	
	//Data value template methods
	DataValueTemplate getDataValueTemplateById(Integer id);
	
	DataValueTemplate getDataValueTemplateByUuid(String uuid);
	
	List<DataValueTemplate> getDataValueTemplateByReportTemplate(ReportTemplate ReportTemplate);
	
	List<DataValueTemplate> getDataValueTemplateByDataElement(DataElement DataElement);

	List<DataValueTemplate> getDataValueTemplateByCategoryOption(CategoryOption CategoryOption);

	DataValueTemplate saveDataValueTemplate(DataValueTemplate DataValueTemplate);
	
	void deleteDataValueTemplate(DataValueTemplate DataValueTemplate);	
	
	//Miscellaneous methods
	
	List<CategoryOption> getCategoryComboOptionsByUid(String uid);
	
	List<Option> getOptionSetOptionsByCode(String uid) ;

}
