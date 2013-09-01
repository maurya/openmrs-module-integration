package org.openmrs.module.integration.api;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.DataValueTemplate;

import org.openmrs.module.integration.api.impl.DhisServiceImpl;

public class DhisServiceTest {
	
	@Test
	public void shouldCheckObjectValues() throws Exception {
	DhisService dhis=new DhisServiceImpl();
	ReportTemplate rt=new ReportTemplate();
	rt=dhis.getReportTemplateById(3);
    List<DataValueTemplate> dvt=dhis.getDataValueTemplateByReportTemplate(rt);
	for(DataValueTemplate d: dvt) 
    Assert.assertNotNull(d.getCategoryOption());
	 }

}
