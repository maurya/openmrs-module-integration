package org.openmrs.module.integration.web.controller.portlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.CohortService;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.web.controller.PortletController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MappingCohortController extends PortletController {
	
	private static final Log log = LogFactory.getLog(MappingCohortController.class);
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model
			) {
		if (log.isDebugEnabled())
			log.debug("In MappingCohort...");
		
		model.put("mapping", "mapping cohort");
		CohortDefinitionService cs=Context.getService(CohortDefinitionService.class);
		List<CohortDefinition> cohortList=cs.getAllDefinitions(false);
		String s=request.getParameter("portletId");
		//model.put("portletId", request.getParameter("portletId"));
		//model.put("mappedCohort", request.getParameter("mappedCohort"));
		model.put("cohorts", cohortList);
		

		
	}
}
