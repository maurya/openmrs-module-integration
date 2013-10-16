package org.openmrs.module.integration.api.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorAndDimensionDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorAndDimensionDataSetDefinition.CohortIndicatorAndDimensionSpecification;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.definition.DefinitionContext;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.util.OpenmrsClassLoader;

public class DhisReportingUtils {
	private static Log log = LogFactory.getLog(DhisReportingUtils.class);
	
	public static void createReportObject(ReportTemplate reportTemplate,String uuid,String reportName){
		//services
		Thread.currentThread().setContextClassLoader(OpenmrsClassLoader.getInstance());
		ReportDefinitionService rds= Context.getService(ReportDefinitionService.class);
		CohortDefinitionService cds= Context.getService(CohortDefinitionService.class);
		ReportService rs = Context.getService(ReportService.class);
		if(uuid==null){
			
		//new objects
		ReportDefinition rd= new ReportDefinition();
		CohortIndicator ci;
		CohortDefinition cd;
		CohortDefinitionDimension cdd=new CohortDefinitionDimension();
		CohortIndicatorAndDimensionSpecification cids = null;
		CohortIndicatorAndDimensionDataSetDefinition cidsd;
		//sets needed for maintenance
		Set<CohortIndicator> indicatorSet = new HashSet<CohortIndicator>();
		Set<CohortDefinitionDimension> dimensionSet= new HashSet<CohortDefinitionDimension>();
		Set<DataElement> dataElementSet= reportTemplate.getDataElements();
		Set<CategoryCombo> categoryComboSet= new HashSet<CategoryCombo>();
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		
		Map<CategoryCombo,List<CohortIndicatorAndDimensionSpecification>> datasetToIndicatorLink=new HashMap<CategoryCombo,List<CohortIndicatorAndDimensionSpecification>>();
		List<CohortIndicatorAndDimensionSpecification> sampler=new ArrayList<CohortIndicatorAndDimensionSpecification>();
		
		rd.setName(reportName);
		
		
		//creating indicators
		for(DataElement de: dataElementSet){	
			cd=cds.getDefinitionByUuid(de.getCohortDefinitionUuid());
			categoryComboSet.add(de.getCategoryCombo());
			ci=new CohortIndicator();
			ci.setCohortDefinition(cd, "");
			cids.setIndicator(new Mapped<CohortIndicator> (ci,params));
			indicatorSet.add(new CohortIndicator(de.getName()));
			
			if(datasetToIndicatorLink.containsKey(de.getCategoryCombo())){	
				sampler=datasetToIndicatorLink.get(de.getCategoryCombo());
				
			}
			else{
				
				sampler=new ArrayList<CohortIndicatorAndDimensionSpecification>();
			}
			sampler.add(cids);
			datasetToIndicatorLink.put(de.getCategoryCombo(),sampler);
		}
		
		//building the dataset definitions based on categorycombo set
		for(CategoryCombo cc:categoryComboSet){
			
		cidsd = new CohortIndicatorAndDimensionDataSetDefinition();
		 sampler = datasetToIndicatorLink.get(cc);
		 //add specifications/indicators/dataelements to DataSetDefinition
		 for(CohortIndicatorAndDimensionSpecification iter:sampler){
			 cidsd.addSpecification(iter);
		 }
		 //add the dimensions
		 for(OptionSet os: cc.getOptionSets()){
			 cdd=new CohortDefinitionDimension();
			 
			 cdd.setName(os.getName());
			 //add the dimension options
			 for(Option o:os.getOptions()){				 
				 cdd.addCohortDefinition(o.getName(), new Mapped<CohortDefinition>(cds.getDefinitionByUuid(o.getCohortdefUuid()),params));
			 }
			 cidsd.addDimension(os.getName(), new Mapped<CohortDefinitionDimension>(cdd,params));
		 }
		 DefinitionContext.getDataSetDefinitionService().saveDefinition(cidsd);

		}
		
	}
	}
}
