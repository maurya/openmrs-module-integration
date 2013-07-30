package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class DataElement extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int dataElementId;
	private String uuid;
	private String dataElementName;
	private String dataElementCode;
	private String dataElementUid;
	private String dataElementType;
	private String dataElementMappedObjectType;
	private Integer dataElementMappedObjectId;
	private Date lastUpdated;
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getDataElementId();
	}

	@Override
	public void setId(Integer id) {
		setDataElementId(id);		
	}

	public int getDataElementId() {
		return dataElementId;
	}

	public void setDataElementId(int dataElementId) {
		this.dataElementId = dataElementId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getDataElementName() {
		return dataElementName;
	}

	public void setDataElementName(String dataElementName) {
		this.dataElementName = dataElementName;
	}
	
	public String getDataElementCode() {
		return dataElementCode;
	}

	public void setDataElementCode(String dataElementCode) {
		this.dataElementCode = dataElementCode;
	}
	
	public String getDataElementUid() {
		return dataElementUid;
	}

	public void setDataElementUid(String dataElementUid) {
		this.dataElementUid = dataElementUid;
	}
	public String getDataElementType() {
		return dataElementType;
	}

	public void setDataElementType(String dataElementType) {
		this.dataElementType = dataElementType;
	}
	
	public String getDataElementMappedObjectType() {
		return dataElementMappedObjectType;
	}

	public void setDataElementMappedObjectType(String dataElementMappedObjectType) {
		this.dataElementMappedObjectType = dataElementMappedObjectType;
	}
	public int getDataElementMappedObjectId() {
		return dataElementMappedObjectId;
	}

	public void setDataElementMappedObjectId(int dataElementMappedObjectId) {
		this.dataElementMappedObjectId = dataElementMappedObjectId;
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
