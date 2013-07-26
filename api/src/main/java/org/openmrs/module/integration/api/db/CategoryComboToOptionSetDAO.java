package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.CategoryComboToOptionSet;

public interface CategoryComboToOptionSetDAO  {
	
	List<CategoryComboToOptionSet> getCategoryComboToOptionSetByCategoryComboId(int id);
	
	List<CategoryComboToOptionSet> getCategoryComboToOptionSetByOptionSetId(int id);

}
