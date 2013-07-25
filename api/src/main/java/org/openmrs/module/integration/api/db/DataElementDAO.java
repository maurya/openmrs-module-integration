package org.openmrs.module.integration.api.db;

import java.util.List;

import org.openmrs.module.integration.DataElement;

public interface DataElementDAO {
	
	DataElement getDataElementById(Integer id);
	
	DataElement getDataElementByUuid(String uuid);
	
	DataElement getDataElementByName(String name);
	
	DataElement getDataElementByCode(String code);
	
	DataElement getDataElementByUid(String uid);
	
	List<DataElement> getDataElementsByServer(int id);
	
	
	DataElement saveDataElement(DataElement DataElement);
	
	DataElement deleteDataElement(DataElement DataElement);	

}
