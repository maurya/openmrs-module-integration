package org.openmrs.module.integration.api;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.metadata.ClassMetadata;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.integration.CategoryCombo;
import org.openmrs.module.integration.CategoryOption;
import org.openmrs.module.integration.DataElement;
import org.openmrs.module.integration.IntegrationServer;
import org.openmrs.module.integration.Option;
import org.openmrs.module.integration.OptionSet;
import org.openmrs.module.integration.ReportTemplate;
import org.openmrs.module.integration.api.db.IntegrationException;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.annotation.Rollback;

public class ServerMetadataTest extends BaseModuleContextSensitiveTest {

	private IntegrationServer is;
	private DhisService ds;
	
	@Override
	public Boolean useInMemoryDatabase() {
		return false;
	}
	
	
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

	@Rollback(false)
	@Test
	public void BuildObjects_shouldWorkForExistingServer() {
		final String server = "dhis";
		String s = null;
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.buildDBObjects(server);
		} catch (IntegrationException e) {
			s = e.getMessage();
		}
		
		DhisService ds = Context.getService(DhisService.class);
		StringBuilder sb = new StringBuilder();
		
		sb.append("CATEGORY COMBOS");
		List<CategoryCombo> ccs = ds.getCategoryComboByServer(ds.getIntegrationServerByName(server));
		for (CategoryCombo cc : ccs) {
			sb.append("\r\n");
			sb.append(cc.getUid());
			sb.append("\t");
			sb.append(cc.getName());
			sb.append("\t");
			sb.append(cc.getCode());
			sb.append("\r\n");
			sb.append("Option Sets:");
			for (OptionSet os : cc.getOptionSets()) {
				sb.append("\t");
				sb.append(os.getName());
			}
			sb.append("\r\n");
			sb.append("Cat Options:");
			for (CategoryOption co : cc.getCategoryOptions()) {
				sb.append("\t");
				sb.append(co.getName());
			}
		}
		sb.append("\r\n");
			
		sb.append("CATEGORY OPTIONS");
		List<CategoryOption> cos = ds.getCategoryOptionByServer(ds.getIntegrationServerByName(server));
		for (CategoryOption co : cos) {
			sb.append("\r\n");
			sb.append(co.getUid());
			sb.append("\t");
			sb.append(co.getName());
			sb.append("\t");
			sb.append(co.getCode());
			sb.append("\r\n");
			sb.append("Options:");
			for (Option ov : co.getOptions()) {
				sb.append("\t");
				sb.append(ov.getName());
			}
			sb.append("\r\n");
			sb.append("Cat Combos:");
			for (CategoryCombo cc : co.getCategoryCombos()) {
				sb.append("\t");
				sb.append(cc.getName());
			}
			sb.append("\r\n");
		}
		sb.append("\r\n");
		
		Assert.assertNotNull("Exception has been thrown: "+s,s);
	}
}
