package org.openmrs.module.integration.api.impl;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.metadata.ClassMetadata;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.UndefinedCohortDefinition;
import org.openmrs.module.integration.api.DhisService;
import org.openmrs.module.integration.api.db.DhisDAO;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.module.reporting.cohort.definition.AllPatientsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class DhisXmlUtilsTest extends BaseModuleContextSensitiveTest {

	private IntegrationServer is;
	private DhisXmlUtils dxu = new DhisXmlUtils();
	private DhisService ds;

//	@Override
//	public Boolean useInMemoryDatabase() {
//		return false;
//	}
	
	@Before
	public void setup() {
		is = new IntegrationServer();
		is.setServerName("dhis");
		is.setServerDescription("DHIS demo server");
		is.setUrl("http://apps.dhis2.org/demo");
		is.setUserName("admin");
		is.setPassword("district");
		ds=Context.getService(DhisService.class);
	}
	
	@Test
	public void DhisXmlUtils_shouldSeeDhisObjects() {
		int n=0;
		Map<String,ClassMetadata> h=ds.getHibernateClassMetadata();
		for (String s : h.keySet()) {
			if (s.equals("IntegrationServer")) n++;
			else if (s.equals("ReportTemplate")) n++;
			else if (s.equals("DataValueTemplate")) n++;
			else if (s.equals("CategoryCombo")) n++;
			else if (s.equals("CategoryOption")) n++;
			else if (s.equals("OptionSet")) n++;
			else if (s.equals("Option")) n++;
			else if (s.equals("DataElement")) n++;
			else if (s.equals("OrgUnit")) n++;
		}
		h=null;
		Assert.assertEquals("Dhis objects are missing",n,9);
	}

	@Test
	public void createNewServer_shouldWorkForResources(){
		String s = dxu.createNewServer("RESOURCES","MasterTemplate.xml", "CategoryOptionCombo-Detailed.xml", "Categories-Export.xml","OrganisationUnit-Export.xml");
		
		Assert.assertEquals("Error is returned:" + s,s,"");
		Assert.assertNotNull("Orgs is null", ds.getIntegrationServerByName("RESOURCES"));		
	}
	
	@Ignore
	@Test
	public void getAllPatients_shouldAddCohortDefOnlyIfNecessary() {
		CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
		List<CohortDefinition> defs = cds.getAllDefinitions(true);
		int nBefore=0;
		for (CohortDefinition cd : defs) {
			if (cd.getClass().equals(AllPatientsCohortDefinition.class)) {
				nBefore++;
			}
		}
		CohortDefinition allPat = dxu.getAllPatients();
		defs = cds.getAllDefinitions(true);
		int nAfter=0;
		for (CohortDefinition cd : defs) {
			if (cd.getClass().equals(AllPatientsCohortDefinition.class)) {
				nAfter++;
			}
		}
		
		if (nBefore==0 && nAfter==0) {
			Assert.assertEquals("All patients was not created", nAfter,1);
		} else if (nBefore==0) {
			Assert.assertEquals("More than one all patients was created", nAfter, 1);
			allPat = dxu.getAllPatients();
			defs = cds.getAllDefinitions(true);
			nAfter=0;
			for (CohortDefinition cd : defs) {
				if (cd.getClass().equals(AllPatientsCohortDefinition.class)) {
					nAfter++;
				}
			}
			Assert.assertEquals("More than one all patients was created", nAfter, 1);
		} else {
			Assert.assertEquals("No all patients should be created", nAfter, nBefore);
		}
		Assert.assertNotNull("AllPatients returned null",allPat);
	}

	@Ignore
	@Test
	public void getUndefined_shouldAddCohortDefOnlyIfNecessary() {
		CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
		List<CohortDefinition> defs = cds.getAllDefinitions(true);
		int nBefore=0;
		for (CohortDefinition cd : defs) {
			if (cd.getClass().equals(UndefinedCohortDefinition.class)) {
				nBefore++;
			}
		}
		CohortDefinition undefined = dxu.getUndefinedCohort();
		defs = cds.getAllDefinitions(true);
		int nAfter=0;
		for (CohortDefinition cd : defs) {
			if (cd.getClass().equals(UndefinedCohortDefinition.class)) {
				nAfter++;
			}
		}
		
		if (nBefore==0 && nAfter==0) {
			Assert.assertEquals("Undefined cohort def was not created", nAfter,1);
		} else if (nBefore==0) {
			Assert.assertEquals("More than one undefined cohort def was created", nAfter, 1);
			undefined = dxu.getUndefinedCohort();
			defs = cds.getAllDefinitions(true);
			nAfter=0;
			for (CohortDefinition cd : defs) {
				if (cd.getClass().equals(UndefinedCohortDefinition.class)) {
					nAfter++;
				}
			}
			Assert.assertEquals("More than one undefined cohort def was created", nAfter, 1);
		} else {
			Assert.assertEquals("No undefined cohort def should be created", nAfter, nBefore);
		}
		Assert.assertNotNull("AllPatients returned null",undefined);
	}
	
}
