package org.openmrs.module.integration.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.openmrs.module.reporting.report.renderer.SimpleHtmlReportRenderer;
import org.openmrs.module.reporting.web.renderers.WebReportRenderer;

@Controller
public class RunReportsController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/runReports", method = RequestMethod.GET)
	public void showRunReports(ModelMap model) {
		
		List<ReportTemplate> reports = new ArrayList<ReportTemplate>();
		DhisService dhisService = Context.getService(DhisService.class);
		ReportDefinitionService rds= Context.getService(ReportDefinitionService.class);
		reports=dhisService.getAllReportTemplates();
		ReportService rs=Context.getService(ReportService.class);
		LocationService ls=Context.getService(LocationService.class);
	ReportDefinition rd=rds.getDefinitionByUuid("ba0bf442-a564-472b-83e5-4991d6c1c539");
	CohortDefinitionService cs=Context.getService(CohortDefinitionService.class);
	@SuppressWarnings("unchecked")
	Mapped <CohortDefinition> cd= (Mapped<CohortDefinition>) cs.getDefinitionByUuid("89f50270-3adf-4b2c-b31e-41a756f02e5b");
	rd.setBaseCohortDefinition(cd);
	Map<String,Object> mapping=new HashMap<String, Object>();
	for(Parameter p: rd.getParameters()){
		if(p.getLabelOrName()=="Location"){
			p.setDefaultValue(ls.getLocationAttributeByUuid("8d6c993e-c2cc-11de-8d13-0010c6dffd0f"));
			mapping.put(p.getLabelOrName(),ls.getLocationAttributeByUuid("8d6c993e-c2cc-11de-8d13-0010c6dffd0f"));
		}
		else if (p.getLabelOrName()=="Start date"){
			p.setDefaultValue("01/09/2003");
			mapping.put(p.getLabelOrName(),"01/09/2003");
		}
		else if (p.getLabelOrName()=="End date"){
			p.setDefaultValue("04/09/2013");
			mapping.put(p.getLabelOrName(),"04/09/2013");
		}
		
		mapping.put(p.getLabelOrName(), p.getDefaultValue());
	}
	Mapped<ReportDefinition> mrd=new Mapped<ReportDefinition>(rd,mapping);
	ReportRequest rr=new ReportRequest();
	RenderingMode rm=new RenderingMode();
	//ReportRenderer reportrenderer=new webreportren
	SimpleHtmlReportRenderer shrr=new SimpleHtmlReportRenderer();		
	rm.setRenderer(shrr);
	rr.setReportDefinition(mrd);
	rr.setRenderingMode(rm);
	rs.runReport(rr);
		model.addAttribute("reports",reports);
		List<ReportDefinition> reportList=rds.getAllDefinitions(false);
		Map<String,ReportDefinition> uuidToReportDefinitionMap=new HashMap<String, ReportDefinition>();
		for(ReportDefinition r: reportList){

			uuidToReportDefinitionMap.put(r.getUuid(), r);
		}
		model.addAttribute("uuidToReportDefinitionMap",uuidToReportDefinitionMap);
	}

}
