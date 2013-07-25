package org.openmrs.module.integration.api.db;

public interface CategoryComboOptionSetLinkDAO {
	
	int[] getOptionSetIdsByCategoryComboId(int id);
	
	int getCategoryComboIdByOptionSetId(int id);

}
