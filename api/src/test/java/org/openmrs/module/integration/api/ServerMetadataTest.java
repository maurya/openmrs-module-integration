package org.openmrs.module.integration.api;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.metadata.ClassMetadata;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class ServerMetadataTest extends BaseModuleContextSensitiveTest {

	private IntegrationServer is;
	private DhisService ds;
	
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
	public void ServerMetadata_shouldSeeDhisObjects() {
		int n=0;
		Map<String,ClassMetadata> h=ds.getHibernateClassMetadata();
		for (String s : h.keySet()) {
			if (s.contains("IntegrationServer")) n++;
			else if (s.contains("ReportTemplate")) n++;
			else if (s.contains("DataValueTemplate")) n++;
			else if (s.contains("CategoryCombo")) n++;
			else if (s.contains("CategoryOption")) n++;
			else if (s.contains("OptionSet")) n++;
			else if (s.contains("Option")) n++;
			else if (s.contains("DataElement")) n++;
			else if (s.contains("OrgUnit")) n++;
		}
		h=null;
		Assert.assertEquals("Dhis objects are missing",n,9);
	}

	@Test
	public void BuildObjects_shouldWorkForExistingServer() {
		final String server = "MasterTemplatePlus";
		String s = null;
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.buildDBObjects(server);
		} catch (IntegrationException e) {
			s = e.getMessage();
		}
		
		
		Assert.assertNotNull("Exception has been thrown",s);
	}
}
