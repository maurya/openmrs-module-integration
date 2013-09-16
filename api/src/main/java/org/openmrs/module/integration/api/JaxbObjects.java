package org.openmrs.module.integration.api;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.openmrs.module.integration.api.jaxb.CategoriesType;
import org.openmrs.module.integration.api.jaxb.CategoryCombosType;
import org.openmrs.module.integration.api.jaxb.DataValueSet;
import org.openmrs.module.integration.api.jaxb.MetaData;
import org.openmrs.module.integration.api.jaxb.OrgUnitType;
import org.openmrs.module.integration.api.jaxb.ReportTemplates;

public class JaxbObjects {

//	singleton
	
	private static JAXBContext jc;
	private static Unmarshaller um;
	private static Marshaller mm;
	static {
		try {
			jc = JAXBContext.newInstance(ReportTemplates.class,MetaData.class,
					CategoriesType.class,CategoryCombosType.class,OrgUnitType.class,
					DataValueSet.class);
			um = jc.createUnmarshaller();
			mm = jc.createMarshaller();
		} catch (JAXBException e) {
			jc= null;
			um = null;
			mm = null;
		}
	}
	
	private JaxbObjects() {};
	
	public static JAXBContext getJC() {
		return jc;
	}
	
	public static Unmarshaller getUM() {
		return um;
	}
	
	public static Marshaller getMM() {
		return mm;
	}

}
