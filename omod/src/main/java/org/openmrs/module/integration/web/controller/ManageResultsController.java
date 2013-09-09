package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.PriorityComparator;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageResultsController {
	protected final Log log = LogFactory.getLog(getClass());
	@RequestMapping(value = "/module/integration/manageResults", method = RequestMethod.GET)
	public void showResultsList(ModelMap model,
			@RequestParam(value="reportDefinition", required=false) ReportDefinition reportDefinition,
			  @RequestParam(value="requestedBy", required=false) User requestedBy,
			  @RequestParam(value="statuses", required=false) Status[] statuses,
			  @RequestParam(value="requestOnOrAfter", required=false) Date requestOnOrAfter,
			  @RequestParam(value="requestOnOrBefore", required=false) Date requestOnOrBefore) {

		Status[] historyStatuses = new Status[] {Status.COMPLETED, Status.SAVED, Status.FAILED};
		model.addAttribute("historyStatuses", historyStatuses);
		if (statuses == null) {
			statuses = historyStatuses;
		}
		
		model.addAttribute("reportDefinition", reportDefinition);
		model.addAttribute("requestedBy", requestedBy);
		model.addAttribute("statuses", Arrays.asList(statuses));
		model.addAttribute("requestOnOrAfter", requestOnOrAfter);
		model.addAttribute("requestOnOrBefore", requestOnOrBefore);
		ReportService rs= Context.getService(ReportService.class);
		List<ReportRequest> history = rs.getReportRequests(reportDefinition, requestOnOrAfter, requestOnOrBefore, statuses);
		if (requestedBy != null) {
			for (Iterator<ReportRequest> i = history.iterator(); i.hasNext();) {
				ReportRequest rr = i.next();
				if (!rr.getRequestedBy().equals(requestedBy)) {
					i.remove();
				}
			}
		}
		Collections.sort(history, new PriorityComparator());
		Collections.reverse(history);
		model.addAttribute("history", history);
		
		model.addAttribute("cached", rs.getCachedReports().keySet());
		
		List<RenderingMode> renderingModes = new ArrayList<RenderingMode>();
		for (ReportRequest reportRequest : history) {
			for (RenderingMode mode : rs.getRenderingModes(reportRequest.getReportDefinition().getParameterizable())) {
				if (OpenmrsUtil.nullSafeEquals(mode, reportRequest.getRenderingMode())) {
					reportRequest.setRenderingMode(mode);
				}
			}
        }
		model.addAttribute("renderingModes", renderingModes);
	}
}
