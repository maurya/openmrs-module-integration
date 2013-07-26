package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class ReportTemplate extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int reportTemplateId;
	private String uuid;
	private String reportTemplateName;
	private String reportTemplateCode;
	private String frequency;
	private String reportTemplateMaster;
	private int mappedReportId;
	private Date lastUpdated;
	private int serverId;

	@Override
	public Integer getId() {
		return getReportTemplateId();
	}

	@Override
	public void setId(Integer id) {
		setReportTemplateId(id);		
	}

	public int getReportTemplateId() {
		return reportTemplateId;
	}

	public void setReportTemplateId(int reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getReportTemplateName() {
		return reportTemplateName;
	}

	public void setReportTemplateName(String reportTemplateName) {
		this.reportTemplateName = reportTemplateName;
	}
	
	public String getReportTemplateCode() {
		return reportTemplateCode;
	}

	public void setReportTemplateCode(String reportTemplateCode) {
		this.reportTemplateCode = reportTemplateCode;
	}
	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getReportTemplateMaster() {
		return reportTemplateMaster;
	}

	public void setReportTemplateMaster(String reportTemplateMaster) {
		this.reportTemplateMaster = reportTemplateMaster;
	}
	public int getMappedReportiId() {
		return mappedReportId;
	}

	public void setMappedReportId(int mappedReportId) {
		this.mappedReportId = mappedReportId;
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