package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategoryComboToOptionSet {

	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryComboOptionSetId;
	private int categoryComboId;
	private int optionSetId;
	
	public int getCategoryComboOptionSetId() {
		return categoryComboOptionSetId;
	}

	public void setCategoryComboOptionSetId(int categoryComboOptionSetId) {
		this.categoryComboOptionSetId = categoryComboOptionSetId;
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
