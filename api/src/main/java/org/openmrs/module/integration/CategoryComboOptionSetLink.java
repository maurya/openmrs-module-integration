package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategoryComboOptionSetLink {

	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryComboOptionSetLinkId;
	private int categoryComboId;
	private int optionSetId;
	
	public int getCategoryComboOptionSetLinkId() {
		return categoryComboOptionSetLinkId;
	}

	public void setCategoryComboOptionSetLinkId(int categoryComboOptionSetLinkId) {
		this.categoryComboOptionSetLinkId = categoryComboOptionSetLinkId;
	}
	public int getCategoryComboId() {
		return categoryComboId;
	}

	public void setCategoryComboId(int categoryComboId) {
		this.categoryComboId = categoryComboId;
	}
	public int getOptionSetId() {
		return optionSetId;
	}

	public void setOptionSetId(int optionSetId) {
		this.optionSetId = optionSetId;
	}
}
