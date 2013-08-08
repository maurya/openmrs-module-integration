package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		model.addAttribute("integrationServer", new IntegrationServer());
	}
    @RequestMapping("/module/integration/deleteServer")
    public String purgeServer(@RequestParam(required=false, value="serverName") String serverName) {

    	IntegrationServer server = new IntegrationServer();
		try {
			
			DhisService dhisService = Context.getService(DhisService.class);	
			server=dhisService.getIntegrationServerByName(serverName);
			dhisService.deleteIntegrationServer(server);
        }
        catch (Exception e) {
	        log.error("unable to get the file", e);
        }		
    	return "redirect:/module/integration/integrationServerAdmin.form";
    } 
    
    @RequestMapping(value = "/module/integration/getServerDetails", method = RequestMethod.POST)
	public @ResponseBody
	void getTemplate(@RequestParam(value="serverName",required=true)String serverName, ModelMap model) {
    	IntegrationServer server = new IntegrationServer();
		try {
			
			DhisService dhisService = Context.getService(DhisService.class);	
			server=dhisService.getIntegrationServerByName(serverName);
			model.put("integrationServer",server);
        }
        catch (Exception e) {
	        log.error("unable to get the file", e);
        }		
		
	}
    
    @RequestMapping(value = "/module/integration/saveIntegrationServer", method = RequestMethod.POST)
    public String saveServer(@ModelAttribute(value="integrationServer") IntegrationServer server,
            HttpServletRequest request){
		try {
			
			DhisService dhisService = Context.getService(DhisService.class);	
			dhisService.saveIntegrationServer(server);
        }
        catch (Exception e) {
	        log.error("unable to save the server", e);
        }		
		return "redirect:/module/integration/integrationServerAdmin.form";
    }
}
