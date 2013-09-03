/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.integration.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.query.service.CohortQueryService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Priority;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The main controller.
 */
@Controller
public class  IntegrationManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		CohortQueryService cqs= Context.getService(CohortQueryService.class);
		//Cohort cohort = cqs.getPatientsHavingEncounters(null,null, TimeQualifier.ANY, null, null, null, null, null, null, null, null, null);
		Cohort cohort = cqs.getPatientsWithGender(true, false, false);
		String s=cohort.getCommaSeparatedPatientIds()+"";
		model.addAttribute("patients",s);
		model.addAttribute("count",cohort.getSize());
	}
	@RequestMapping(value = "/module/integration/manage", method = RequestMethod.POST)
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReportDefinitionService rds = Context.getService(ReportDefinitionService.class);
		ReportDefinition reportDefinition=rds.getDefinitionByUuid("3217bf6e-5a51-4331-b5e2-35761cc1ff18");
		ReportService rs = Context.getService(ReportService.class);
		ReportRequest rr= new ReportRequest();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		/*for (Parameter parameter : reportDefinition.getParameters()) {
			Object value = null;
			value = WidgetUtil.parseInput(value, parameter.getType(), parameter.getCollectionType());
			
			params.put(parameter.getName(), value);
		}*/
		RenderingMode rm=new RenderingMode();
		List<RenderingMode> renderingModes=rs.getRenderingModes(reportDefinition);
		try {
		Class<? extends ReportRenderer> rc = (Class<? extends ReportRenderer>) Context.loadClass("org.openmrs.module.reporting.web.renderers.DefaultWebRenderer");
		String arg = null;
		
		for (RenderingMode mode : renderingModes) {
			if (mode.getRenderer().getClass().equals(rc) && OpenmrsUtil.nullSafeEquals(mode.getArgument(), arg)) {
				rm=mode;
			}
		}
		}
		catch (Exception e) {
			log.warn("Could not load requested renderer", e);
		}
		rr.setReportDefinition(new Mapped<ReportDefinition>(reportDefinition, params));
		rr.setBaseCohort(null);
	    rr.setRenderingMode(rm);
	    rr.setPriority(Priority.NORMAL);
	    rr.setSchedule("");
		
	    rr = rs.queueReport(rr);
		rs.processNextQueuedReports();
		//new RedirectView("../reports/reportHistoryOpen.form?uuid="+rr.getUuid());
		return new ModelAndView(new RedirectView("../../module/reporting/reports/reportHistoryOpen.form?uuid="+rr.getUuid()));
	}
	
}
