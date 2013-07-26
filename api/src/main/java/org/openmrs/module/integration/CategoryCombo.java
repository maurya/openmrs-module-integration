package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryCombo extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryComboId;
	private String uuid;
	private String categoryComboName;
	private String categoryComboCode;
	private String categoryComboValueName;
	private String categoryComboValueCode;
	private Date lastUpdated;
	private int serverId;

	@Override
	public Integer getId() {
		return getCategoryComboId();
	}

	@Override
	public void setId(Integer id) {
		setCategoryComboId(id);		
	}

	public int getCategoryComboId() {
		return categoryComboId;
	}

	public void setCategoryComboId(int categoryComboId) {
		this.categoryComboId = categoryComboId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCategoryComboName() {
		return categoryComboName;
	}

	public void setCategoryComboName(String categoryComboName) {
		this.categoryComboName = categoryComboName;
	}
	
	public String getCategoryComboCode() {
		return categoryComboCode;
	}

	public void setCategoryComboCode(String categoryComboCode) {
		this.categoryComboCode = categoryComboCode;
	}
	
	public String getCategoryComboValueName() {
		return categoryComboValueName;
	}

	public void setCategoryComboValueName(String categoryComboValueName) {
		this.categoryComboValueName = categoryComboValueName;
	}
	public String getCategoryComboValueCode() {
		return categoryComboValueCode;
	}

	public void setCategoryComboValueCode(String categoryComboValueCode) {
		this.categoryComboValueCode = categoryComboValueCode;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}