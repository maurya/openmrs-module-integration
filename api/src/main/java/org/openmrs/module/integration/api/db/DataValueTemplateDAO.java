package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.DataValueTemplate;

public interface DataValueTemplateDAO {
	
	DataValueTemplate getDataValueTemplateById(Integer id);
	
	DataValueTemplate getDataValueTemplateByUuid(String uuid);
	
	List<DataValueTemplate> getDataValueTemplateByReportTemplate(int ReportTemplateId);
	
	List<DataValueTemplate> getDataValueTemplateByDataElementId(int DataElementId);

	List<DataValueTemplate> getDataValueTemplateByCategoryComboId(int CategoryComboId);

	DataValueTemplate saveDataValueTemplate(DataValueTemplate DataValueTemplate);
	
	void deleteDataValueTemplate(DataValueTemplate DataValueTemplate);	
}
