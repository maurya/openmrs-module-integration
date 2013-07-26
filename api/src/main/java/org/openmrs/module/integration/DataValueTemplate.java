package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class DataValueTemplate extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int dataValueTemplateId;
	private String uuid;
	private int dataElementId;
	private int categoryComboId;
	private int reportTemplateId;
	private Date lastUpdated;

	@Override
	public Integer getId() {
		return getValueTemplateId();
	}

	@Override
	public void setId(Integer id) {
		setValueTemplateId(id);		
	}

	public int getValueTemplateId() {
		return dataValueTemplateId;
	}

	public void setValueTemplateId(int dataValueTemplateId) {
		this.dataValueTemplateId = dataValueTemplateId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getDataElementId() {
		return dataElementId;
	}

	public void setDataElementId(int dataElementId) {
		this.dataElementId = dataElementId;
	}
	public int getCategoryComboId() {
		return categoryComboId;
	}

	public void setCategoryComboId(int categoryComboId) {
		this.categoryComboId = categoryComboId;
	}
	public int getReportTemplateId() {
		return reportTemplateId;
	}

	public void setReportTemplateId(int reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}