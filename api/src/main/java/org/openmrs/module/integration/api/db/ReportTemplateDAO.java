package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.ReportTemplate;

public interface ReportTemplateDAO {

	ReportTemplate getReportTemplateById(Integer id);
	
	ReportTemplate getReportTemplateByUuid(String uuid);
	
	List<ReportTemplate> getChildReportTemplatesByMasterTemplate(String masterTemplate);
	
	List<ReportTemplate> getChildReportTemplatesByServer(int id);
	
	ReportTemplate saveChildReportTemplate(ReportTemplate ReportTemplate);
	
	void deleteChildReportTemplate(ReportTemplate ReportTemplate);	


}
