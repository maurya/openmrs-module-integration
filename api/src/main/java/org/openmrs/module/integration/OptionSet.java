package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class OptionSet extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int optionSetId;
	private String optionSetName;
	private String uuid;
	private String optionSetCode;
	private String optionSetValueName;
	private String optionSetValueCode;
	private String mappedObjectType;
	private String mappedObjectId;
	private int serverId;

	@Override
	public Integer getId() {
		return getOptionSetId();
	}

	@Override
	public void setId(Integer id) {
		setOptionSetId(id);		
	}

	public int getOptionSetId() {
		return optionSetId;
	}

	public void setOptionSetId(int optionSetId) {
		this.optionSetId = optionSetId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getOptionSetName() {
		return optionSetName;
	}

	public void setOptionSetName(String optionSetName) {
		this.optionSetName = optionSetName;
	}
	
	public String getOptionSetCode() {
		return optionSetCode;
	}

	public void setOptionSetCode(String optionSetCode) {
		this.optionSetCode = optionSetCode;
	}
	
	public String getOptionSetValueName() {
		return optionSetValueName;
	}

	public void setOptionSetValueName(String optionSetValueName) {
		this.optionSetValueName = optionSetValueName;
	}
	public String getOptionSetValueCode() {
		return optionSetValueCode;
	}

	public void setOptionSetValueCode(String optionSetValueCode) {
		this.optionSetValueCode = optionSetValueCode;
	}
	public String getMappedObjectType() {
		return mappedObjectType;
	}

	public void setMappedObjectType(String mappedObjectType) {
		this.mappedObjectType = mappedObjectType;
	}
	public String getMappedObjectId() {
		return mappedObjectId;
	}

	public void setmappedObjectId(String mappedObjectId) {
		this.mappedObjectId = mappedObjectId;
	}
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}