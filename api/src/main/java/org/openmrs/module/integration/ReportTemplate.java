package org.openmrs.module.integration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class ReportTemplate extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(ReportTemplate.class);

	private int id;
	private String reportTemplateName;
	private String reportTemplateCode;
	private String frequency;
	private String mappedReportUuid;
	private String uid;
	private Date lastUpdated;
	private IntegrationServer integrationServer;
	private Set<DataValueTemplate> dataValueTemplates = new HashSet<DataValueTemplate>(0);

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;		
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
    
    public Set<DataValueTemplate> getDataValueTemplates() {
        return dataValueTemplates;
    }
 
    public void setDataValueTemplates(Set<DataValueTemplate> dataValueTemplates) {
        this.dataValueTemplates = dataValueTemplates;
    }
}