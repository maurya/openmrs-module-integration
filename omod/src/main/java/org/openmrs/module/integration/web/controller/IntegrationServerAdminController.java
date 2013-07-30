package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.DhisService;

@Controller
public class IntegrationServerAdminController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/integrationServerAdmin", method = RequestMethod.GET)
	public void showServerList(ModelMap model) {

		List<IntegrationServer> servers = new ArrayList<IntegrationServer>();
		DhisService dhisService = Context.getService(DhisService.class);
		servers=dhisService.getAllIntegrationServers();
		model.addAttribute("serverItems",servers);
	}
    @RequestMapping("/module/integration/deleteServer")
    public String purgeServer(@RequestParam(required=false, value="serverName") String serverName) {

    	//getReportService().purgeDefinition(getReportService().getDefinitionByUuid(uuid));	
    	return "redirect:/module/integration/integrationServerAdmin";
    } 
    
    @RequestMapping(value = "/module/integration/getServerDetails", method = RequestMethod.POST)
	public @ResponseBody
	IntegrationServer getTemplate(@RequestParam(value="serverName",required=true)String serverName) {
    	IntegrationServer server = new IntegrationServer();
		try {
			
			DhisService dhisService = Context.getService(DhisService.class);	
			server=dhisService.getIntegrationServerByName(serverName);
        }
        catch (Exception e) {
	        log.error("unable to get the file", e);
        }		
		return server;
		
	}
}
