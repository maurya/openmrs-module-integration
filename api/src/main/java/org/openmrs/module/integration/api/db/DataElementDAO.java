package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.DataElement;

public interface DataElementDAO {
	
	DataElement getDataElementById(Integer id);
	
	DataElement getDataElementByUuid(String uuid);
	
	List<DataElement> getDataElementsByServer(int id);
		
	DataElement saveDataElement(DataElement DataElement);
	
	void deleteDataElement(DataElement DataElement);	

}
