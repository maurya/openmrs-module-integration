package org.openmrs.module.integration.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageIntegrationDatasetsController {
	@RequestMapping(value = "/module/integration/manageDatasets", method = RequestMethod.GET)
	public void showServerList(@RequestParam(required=false, value="name") String name,
			ModelMap model) {
		model.addAttribute("serverName",name);
	}

}
