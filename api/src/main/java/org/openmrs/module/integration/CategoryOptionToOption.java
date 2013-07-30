package org.openmrs.module.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategoryOptionToOption {

	public static Log log = LogFactory.getLog(IntegrationServer.class);

	private int categoryOptionToOptionId;
	private CategoryOption categoryOption;
	private Option option;
	
	public int getCategoryOptionToOptionId() {
		return categoryOptionToOptionId;
	}

	public void setCategoryOptionToOptionId(int categoryOptionToOptionId) {
		this.categoryOptionToOptionId = categoryOptionToOptionId;
	}
	public CategoryOption getCategoryOption() {
		return categoryOption;
	}

	public void setCategoryOption(CategoryOption categoryOption) {
		this.categoryOption = categoryOption;
	}
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
}
