package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RunReportsController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/runReports", method = RequestMethod.GET)
	public void showRunReports(ModelMap model) {
		
		List<ReportTemplate> reports = new ArrayList<ReportTemplate>();
		DhisService dhisService = Context.getService(DhisService.class);
		
		reports=dhisService.getAllReportTemplatesMapped();
		
		model.addAttribute("reports",reports);
	}

}
