package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryCombo extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryComboId;
	private String categoryComboName;
	private String uuid;
	private String categoryComboCode;
	private String categoryComboValueName;
	private String categoryComboValueCode;
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
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}