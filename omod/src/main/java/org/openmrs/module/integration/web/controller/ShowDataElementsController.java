package org.openmrs.module.integration.web.controller;

import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.DhisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowDataElementsController {
	@RequestMapping(value = "/module/integration/showDataElements", method = RequestMethod.GET)
	public void showDataElementsAndOptions(@RequestParam(required=false, value="reportTemplateId") int reportTemplateId,
			ModelMap model) {
		DhisService dhisService = Context.getService(DhisService.class);
		ReportTemplate reportTemplate=dhisService.getReportTemplateById(reportTemplateId);
		model.addAttribute("reportTemplate",reportTemplate);
		Map<DataElement,List<CategoryOption>> DataElementToCategoryOptionDictionary= dhisService.getDataElementToCategoryOptionDictionaryByReportTemplate(reportTemplate);
		model.addAttribute("DataElementToCategoryOptionDictionary",DataElementToCategoryOptionDictionary);
		
//		Map<Option,CategoryOption> OptionToCategoryOptionDictionary= dhisService.getOptionToCategoryOptionDictionaryByReportTemplate(reportTemplate);
//		model.addAttribute("OptionToCategoryOptionDictionary",OptionToCategoryOptionDictionary);
	}
	
	@RequestMapping(value = "/module/integration/saveDataElementMapping", method = RequestMethod.POST)
    public void saveReportTemplate(@RequestParam(value = "mappedDataElement", required=true) String mappedDataElement,
    		@RequestParam(value = "id", required=true) String id){
		try {
			if(mappedDataElement!=null){
			DhisService dhisService = Context.getService(DhisService.class);	
			DataElement dataElement=dhisService.getDataElementById(Integer.parseInt((id)));
			
			dataElement.setDataElementMappedObjectId(Integer.parseInt((mappedDataElement)));
			dhisService.saveDataElement(dataElement);
			}
        }
        catch (Exception e) {
	       
        }		
    }

}
