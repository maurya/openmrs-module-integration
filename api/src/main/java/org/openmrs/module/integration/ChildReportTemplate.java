package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class ChildReportTemplate extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int reportTemplateId;
	private String reportTemplateName;
	private String uuid;
	private String reportTemplateCode;
	private String reportTemplateDescription;
	private String reportTemplateMaster;
	private String mappedReportId;
	private boolean reportTemplateMapped;
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
	
	public String getReportTemplateDescription() {
		return reportTemplateDescription;
	}

	public void setReportTemplateDescription(String reportTemplateDescription) {
		this.reportTemplateDescription = reportTemplateDescription;
	}
	public String getReportTemplateMaster() {
		return reportTemplateMaster;
	}

	public void setReportTemplateMaster(String reportTemplateMaster) {
		this.reportTemplateMaster = reportTemplateMaster;
	}
	public String getMappedReportiId() {
		return mappedReportId;
	}

	public void setMappedReportId(String mappedReportId) {
		this.mappedReportId = mappedReportId;
	}
	public boolean getReportTemplateMapped() {
		return reportTemplateMapped;
	}

	public void setmappedObjectId(boolean reportTemplateMapped) {
		this.reportTemplateMapped = reportTemplateMapped;
	}
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}