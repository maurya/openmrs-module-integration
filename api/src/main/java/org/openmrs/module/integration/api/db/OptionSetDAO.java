package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.OptionSet;

public interface OptionSetDAO {
	
	OptionSet getOptionSetById(Integer id);
	
	OptionSet getOptionSetByUuid(String uuid);
	
	List<OptionSet> getOptionSetsByServer(int id);
	
	OptionSet saveOptionSet(OptionSet OptionSet);
	
	void deleteOptionSet(OptionSet OptionSet);	

}
