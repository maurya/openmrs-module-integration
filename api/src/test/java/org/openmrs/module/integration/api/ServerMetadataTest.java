/**
 * 
 */
package org.openmrs.module.integration.api;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.integration.api.db.ServerMetadata;
import org.openmrs.module.integration.api.db.ServerMetadata.ResourceSource;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${ServerMetadata}}.
 */
public class ServerMetadataTest extends BaseModuleContextSensitiveTest {

	@Before
	public void setup() throws Exception {
	}
	
	@Test
	public void ServerMetadata_ShouldLoadFromResource() {
		ServerMetadata sm = new ServerMetadata();
		try {
			sm.getServerMetadata(ResourceSource.MAIN,"MasterTemplate.xml","CategoryOptionCombo-Detailed.xml","Categories-Export.xml");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		Assert.assertNotNull(sm.getMaster());
		Assert.assertNotNull(sm.getCats());
		Assert.assertNotNull(sm.getOpts());
		
	}
}
