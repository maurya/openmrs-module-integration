package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class Option extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int optionId;
	private String uuid;
	private String name;
	private String code;
	private String setName;
	private String setCode;
	private String mappedObjectType;
	private int mappedObjectId;
	private Date lastUpdated;
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getOptionId();
	}

	@Override
	public void setId(Integer id) {
		setOptionId(id);		
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	public String getMappedObjectType() {
		return mappedObjectType;
	}

	public void setMappedObjectType(String mappedObjectType) {
		this.mappedObjectType = mappedObjectType;
	}
	public int getMappedObjectId() {
		return mappedObjectId;
	}

	public void setmappedObjectId(int mappedObjectId) {
		this.mappedObjectId = mappedObjectId;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public void setIntegrationServer(IntegrationServer integrationServer)
    {
        this.integrationServer = integrationServer;
    }

    public IntegrationServer getIntegrationServer()
    {
        return this.integrationServer;
    }
}