package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.ChildReportTemplate;

public interface ChildReportTemplateDAO {

	ChildReportTemplate getChildReportTemplateById(Integer id);
	
	ChildReportTemplate getChildReportTemplateByUuid(String uuid);
	
	ChildReportTemplate getChildReportTemplateByName(String name);
	
	ChildReportTemplate getChildReportTemplateByCode(String code);
	
	ChildReportTemplate getChildReportTemplateByMasterTemplate(String masterTemplate);
	
	List<ChildReportTemplate> getChildReportTemplatesByServer(int id);
	
	
	ChildReportTemplate saveChildReportTemplate(ChildReportTemplate ChildReportTemplate);
	
	ChildReportTemplate deleteChildReportTemplate(ChildReportTemplate ChildReportTemplate);	


}
