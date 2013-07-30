package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageReportTemplatesController {
	@RequestMapping(value = "/module/integration/manageReportTemplates", method = RequestMethod.GET)
	public void showServerList(@RequestParam(required=false, value="name") String name,
			ModelMap model) {
		DhisService dhisService = Context.getService(DhisService.class);
		IntegrationServer server=dhisService.getIntegrationServerByName(name);
		model.addAttribute("server",server);
		List<ReportTemplate> reportTemplates = new ArrayList<ReportTemplate>();
		reportTemplates=dhisService.getReportTemplatesByServer(server);
		model.addAttribute("reportTemplates",reportTemplates);
	}

}
