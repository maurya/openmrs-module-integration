package org.openmrs.module.integration;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class ReportTemplate extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(ReportTemplate.class);

	private int reportTemplateId;
	private String reportTemplateName;
	private String reportTemplateCode;
	private String frequency;
	private String mappedReportUuid;
	private String uid;
	private Date lastUpdated;
	private IntegrationServer integrationServer;

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
	public String getMappedReportUuid() {
		return mappedReportUuid;
	}

	public void setMappedReportUuid(String mappedReportUuid) {
		this.mappedReportUuid = mappedReportUuid;
	}
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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