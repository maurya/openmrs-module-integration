package org.openmrs.module.integration.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IntegrationServerAdminController {
	
	@RequestMapping(value = "/module/integration/integrationServerAdmin", method = RequestMethod.GET)
	public void showServerList(ModelMap model) {
		String [] ServerList={"one","two","Three"};
		model.addAttribute("serverItems",ServerList);
	}
    @RequestMapping("/module/integration/purgeServer")
    public String purgeServer(@RequestParam(required=false, value="name") String name) {

    	//getReportService().purgeDefinition(getReportService().getDefinitionByUuid(uuid));	
    	return "redirect:/module/integration/integrationServerAdmin";
    }  
}
