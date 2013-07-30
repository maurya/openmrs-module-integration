package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class IntegrationServer extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int integrationServerId;
	private String uuid;
	private String serverName;
	private String serverDescription;
	private String userName;
	private String password;
	private String url;
	private String email;
	private String masterTemplate;


	@Override
	public Integer getId() {
		return getIntegrationServerId();
	}

	@Override
	public void setId(Integer id) {
		setIntegrationServerId(id);		
	}

	public int getIntegrationServerId() {
		return integrationServerId;
	}

	public void setIntegrationServerId(int integrationServerId) {
		this.integrationServerId = integrationServerId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String name) {
		this.serverName = name;
	}
	
	public String getServerDescription() {
		return serverDescription;
	}

	public void setServerDescription(String description) {
		this.serverDescription = description;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMasterTemplate() {
		return masterTemplate;
	}

	public void setMasterTemplate(String masterTemplate) {
		this.masterTemplate = masterTemplate;
	}
}
