package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.IntegrationServer;

public interface IntegrationServerDAO {
	
	IntegrationServer getIntegrationServerById(Integer id);
	
	IntegrationServer getIntegrationServerByUuid(String uuid);
	
	IntegrationServer getIntegrationServerByName(String name);
	
	IntegrationServer getIntegrationServerByUrl(String url);
	
	IntegrationServer saveIntegrationServer(IntegrationServer IntegrationServer);
	
	IntegrationServer deleteIntegrationServer(IntegrationServer IntegrationServer);
	
	List<IntegrationServer> getAllIntegrationServers();
	
}
