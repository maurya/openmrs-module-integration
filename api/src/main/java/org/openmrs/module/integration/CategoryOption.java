package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryOption extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryOptionId;
	private String uuid;
	private String name;
	private String code;
	private String uid;
	private String comboName;
	private String comboCode;
	private Date lastUpdated;
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getCategoryOptionId();
	}

	@Override
	public void setId(Integer id) {
		setCategoryOptionId(id);		
	}

	public int getCategoryOptionId() {
		return categoryOptionId;
	}

	public void setCategoryOptionId(int categoryOptionId) {
		this.categoryOptionId = categoryOptionId;
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
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	public String getComboCode() {
		return comboCode;
	}

	public void setComboCode(String comboCode) {
		this.comboCode = comboCode;
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