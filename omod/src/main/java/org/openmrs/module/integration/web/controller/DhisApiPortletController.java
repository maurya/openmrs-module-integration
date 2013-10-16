package org.openmrs.module.integration.web.controller;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.DhisApiResult;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.integration.api.DhisApiService;
import org.openmrs.web.controller.PortletController;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("**/dhisApi.portlet")
@Authorized("Run Reports")
public class DhisApiPortletController extends PortletController {
	
	private static final Log log = LogFactory.getLog(DhisApiPortletController.class);
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		Date asOf;
		Date sent;
		Future<DhisApiResult> future;
		Boolean done;
		
		if (log.isDebugEnabled())
			log.debug("In DhisApiPortlet...");
		
		HttpSession session = request.getSession();
		String operation = request.getParameter("operation");
		String server = request.getParameter("server");
		DhisService ds = Context.getService(DhisService.class);
		DhisApiService as = Context.getService(DhisApiService.class);
		IntegrationServer is = ds.getIntegrationServerByName(server);
		model.remove("done");
		
		if ("TEST".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.testConnection(is);
			session.setAttribute("apiresult", future);
			
		} else if ("CREATE_API".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.createServerMetadata(is);
			session.setAttribute("apiresult", future);
			return;

		} else if ("CREATE_SVR".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.createServerMetadata(server);
			session.setAttribute("apiresult", future);

		} else if ("UPDATE_API".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.updateServerMetadata(is);
			session.setAttribute("apiresult", future);

		} else if ("UPDATE_SVR".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.updateServerMetadata(server);
			session.setAttribute("apiresult", future);

		} else if ("ORGS_API".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.updateServerOrgUnits(is);
			session.setAttribute("apiresult", future);

		} else if ("ORGS_SVR".equalsIgnoreCase(operation)) {
			future = (Future<DhisApiResult>) as.updateServerOrgUnits(server);
			session.setAttribute("apiresult", future);

		} else if ("SEND".equalsIgnoreCase(operation)) {
			String report = request.getParameter("report");
			DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
			try {
				asOf = df.parse(request.getParameter("asof"));
				sent = df.parse(request.getParameter("sent"));
			} catch (ParseException pe) {
				asOf = new Date();
				sent = new Date();
			}
			future = (Future<DhisApiResult>) as.sendFileToServer(server, report, asOf, sent);
			session.setAttribute("apiresult", future);

		} else if ("STATUS".equalsIgnoreCase(operation)) {
			if (session.getAttribute("apiresult")==null) {
				done=false;
			} else {
				future = (Future<DhisApiResult>) session.getAttribute("apiresult");
				done=future.isDone();
			}
			model.put("done",done);
			
		} else if ("CANCEL".equalsIgnoreCase(operation)) {
			if (session.getAttribute("apiresult")==null) {
				done=false;
			} else {
				future = (Future<DhisApiResult>) session.getAttribute("apiresult");
				if (!future.isDone()) {
					future.cancel(true);
					DhisApiResult result = new DhisApiResult();
					result.setError(true);
					result.setStatus("integration.ApiPortlet.Cancelled");
					session.setAttribute("apiresult", new AsyncResult<DhisApiResult> (result));
				}
				done=true;
			}
			model.put("done", done);
			
		}
		return;

	}

}
