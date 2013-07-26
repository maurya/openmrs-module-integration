package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.CategoryCombo;



public interface CategoryComboDAO {
	
	CategoryCombo getCategoryComboById(Integer id);
	
	CategoryCombo getCategoryComboByUuid(String uuid);
	
	List<CategoryCombo> getCategoryComboByServer(int id);
	
	CategoryCombo saveCategoryCombo(CategoryCombo CategoryCombo);
	
	void deleteCategoryCombo(CategoryCombo CategoryCombo);	

}
