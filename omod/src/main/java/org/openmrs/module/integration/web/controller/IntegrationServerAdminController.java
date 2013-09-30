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
import org.openmrs.annotation.Authorized;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.integration.api.db.DhisMetadataUtils;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;

import com.mysql.jdbc.StringUtils;

@Controller
public class IntegrationServerAdminController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/integrationServerAdmin", method = RequestMethod.GET)
	@Authorized("View Server")
	public void showServerList(ModelMap model) {	
		List<IntegrationServer> servers = new ArrayList<IntegrationServer>();
		DhisService dhisService = Context.getService(DhisService.class);
		servers=dhisService.getAllIntegrationServers();
		model.addAttribute("serverItems",servers);
		model.addAttribute("integrationServer", new IntegrationServer());
	}
	
    @RequestMapping(value="/module/integration/deleteServer" ,method = RequestMethod.POST)
    @Authorized("Manage Server")
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
    @Authorized("Manage Server")
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
    
//    @RequestMapping(value = "/module/integration/testServerConnection", method = RequestMethod.POST)
//    @Authorized("Manage Server")
//    public String testConnection(@RequestParam(value="serverName",required=true)  String serverName){
//	
//			
//			DhisService dhisService = Context.getService(DhisService.class);
//			IntegrationServer server=dhisService.getIntegrationServerByName(serverName);
//			String testResult=DhisMetadataUtils.testConnection(server);
//       
//		if(!StringUtils.isNullOrEmpty(testResult))
//			return "failure";
//		
//		return "success";
//    }
    
    @RequestMapping(value = "/module/integration/getServerMetadata", method = RequestMethod.POST)
    @Authorized("Manage Server")
    public void getServerMeatadata(@RequestParam(value="serverName",required=true)  String serverName){
	
			
			DhisService dhisService = Context.getService(DhisService.class);
			IntegrationServer server=dhisService.getIntegrationServerByName(serverName);
			try {
				DhisMetadataUtils.getServerMetadata(server);
			} catch (IntegrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
    }
    
    @RequestMapping(value = "/module/integration/saveIntegrationServer", method = RequestMethod.POST)
    @Authorized("Manage Server")
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
    
    @RequestMapping(value = "/module/integration/testConnection", method = RequestMethod.GET)
    public String testConnection(@RequestParam(required=false, value="name") String serverName) {

       	IntegrationServer server = new IntegrationServer();
       	try {
    			
    		log.info("testing connection " + serverName);
			DhisService dhisService = Context.getService(DhisService.class);	
			server=dhisService.getIntegrationServerByName(serverName);
			if (server==null) server=dhisService.getIntegrationServerById(39);
    		String result = DhisMetadataUtils.testConnection(server);
    		if (result==null) result="";
    		if (result.isEmpty()) result="Connection works";
    		log.info(serverName+": "+result);
    	} catch (Exception e) {
    		log.error("error testing connection "+server.getId(), e);
    	}
		return "redirect:/module/integration/integrationServerAdmin.form";
    }
    
    @RequestMapping(value = "/module/integration/updateServer", method = RequestMethod.GET)
    public String updateServer(@RequestParam(required=false, value="name") String serverName) {
    	ServerMetadata sm;
    	IntegrationServer server = new IntegrationServer();
   	   try {
    		log.info("building server " + serverName);
			DhisService dhisService = Context.getService(DhisService.class);	
			server=dhisService.getIntegrationServerByName(serverName);
			if (server==null) server=dhisService.getIntegrationServerById(38);
			log.info("Got server" + server.getId().toString());
    		if (server.getId().equals(39)) {
    			log.info("39 in");
    			DhisMetadataUtils.getServerMetadata(server);
    			log.info("39 out");
    		}
    		sm = new ServerMetadata();
    		sm.buildDBObjects(serverName);
    		log.info("server "+server.getId()+" built");
       	} catch (Exception e) {
    		log.error("error building server data: " + e.getMessage(), e);
    	}
    	sm = null;
		return "redirect:/module/integration/integrationServerAdmin.form";
    }
    
}
