package org.openmrs.module.integration.api;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class ServerMetadataTest extends BaseModuleContextSensitiveTest {

	private IntegrationServer is;
	
	@Before
	public void setup() {
		is = new IntegrationServer();
		is.setServerName("dhis");
		is.setServerDescription("DHIS demo server");
		is.setUrl("http://apps.dhis2.org/demo");
		is.setUserName("admin");
		is.setPassword("district");
	}
	
	@Test
	public void ServerMetadata_shouldLoadFromResources(){
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata("MasterTemplate.xml", "CategoryOptionCombo-Detailed.xml", "Categories-Export.xml");
		} catch (IntegrationException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull("Master is null", sm.getMaster() );
		Assert.assertNotNull("Categories is null", sm.getCats());
		Assert.assertNotNull("Options is null", sm.getOpts());
	}
	
	@Test
	public void ServerMetadata_shouldGetOrgsFromResources() {
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata("MasterTemplate.xml", "CategoryOptionCombo-Detailed.xml", "Categories-Export.xml");
			sm.getOrgUnits("OrganisationUnit-Export.xml");
		} catch (IntegrationException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull("Orgs is null", sm.getOrgs() );		
	}
	
	@Test
	public void ServerMetadata_shouldGetStatusOfDemoServer() {
		ServerMetadata sm = new ServerMetadata();
		String s = sm.testConnection(is);
		Assert.assertNull(s);
	}

	@Test
	public void ServerMetadata_shouldLoadFromDemoServer(){
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata(is);
		} catch (IntegrationException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull("Master is null", sm.getMaster() );
		Assert.assertNotNull("Categories is null", sm.getCats());
		Assert.assertNotNull("Options is null", sm.getOpts());
	}
	
	@Test
	public void ServerMetadata_shouldGetOrgsFromDemoServer() {
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata(is);
			sm.getOrgUnits();
		} catch (IntegrationException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull("Orgs is null", sm.getOrgs() );		
	}
	
}
