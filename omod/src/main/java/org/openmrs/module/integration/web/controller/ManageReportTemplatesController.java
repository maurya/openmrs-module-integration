package org.openmrs.module.integration.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageReportTemplatesController {
	@RequestMapping(value = "/module/integration/manageReportTemplates", method = RequestMethod.GET)
	public void showServerList(@RequestParam(required=false, value="name") String name,
			ModelMap model) {
		DhisService dhisService = Context.getService(DhisService.class);
		IntegrationServer server=dhisService.getIntegrationServerByName(name);
		model.addAttribute("server",server);
		List<ReportTemplate> reportTemplates = new ArrayList<ReportTemplate>();
		reportTemplates=dhisService.getReportTemplatesByServer(server);
		model.addAttribute("reportTemplates",reportTemplates);
		
		ReportDefinitionService rds=Context.getService(ReportDefinitionService.class);

		List<ReportDefinition> reportList=rds.getAllDefinitions(false);
		Map<String,ReportDefinition> uuidToReportDefinitionMap=new HashMap<String, ReportDefinition>();
		for(ReportDefinition r: reportList){
			
			uuidToReportDefinitionMap.put(r.getUuid(), r);
		}
		model.addAttribute("uuidToReportDefinitionMap",uuidToReportDefinitionMap);
		
		Map<DataElement,CategoryCombo> DataElementToCategoryComboDictionary= new HashMap<DataElement, CategoryCombo>();
		for(ReportTemplate r: reportTemplates){
			DataElementToCategoryComboDictionary.putAll(dhisService.getDataElementToCategoryComboDictionaryByReportTemplate(r));		
		}
		Map<CategoryCombo,List<DataElement>> CategoryComboToDataElementDictionary = new HashMap<CategoryCombo,List<DataElement>>();
		
		List<DataElement> temporaryDataElementList=new ArrayList<DataElement>();
		
		for(Map.Entry<DataElement,CategoryCombo> entry : DataElementToCategoryComboDictionary.entrySet()){
			
			temporaryDataElementList=CategoryComboToDataElementDictionary.get(entry.getValue());
			if(temporaryDataElementList== null){	
				temporaryDataElementList=new ArrayList<DataElement>();
				CategoryComboToDataElementDictionary.put(entry.getValue(), temporaryDataElementList);
			}
			temporaryDataElementList.add(entry.getKey());		
		}
		model.addAttribute("CategoryComboToDataElementDictionary",CategoryComboToDataElementDictionary);
	}
	
	@RequestMapping(value = "/module/integration/saveReportTemplateMapping", method = RequestMethod.POST)
    public void saveReportTemplate(@RequestParam(value = "mappedReport", required=true) String mappedReport,
    		@RequestParam(value = "id", required=true) String id){
		try {
			if(mappedReport!=null){
			DhisService dhisService = Context.getService(DhisService.class);	
			ReportDefinitionService reportDefinitionService=Context.getService(ReportDefinitionService.class);
			ReportTemplate report=dhisService.getReportTemplateById(Integer.parseInt((id)));
			ReportDefinition reportDefinition= reportDefinitionService.getDefinitionByUuid(report.getUuid());
			reportDefinition.setName(mappedReport);
			reportDefinitionService.saveDefinition(reportDefinition);
			}
        }
        catch (Exception e) {
	       
        }		
    }

}
