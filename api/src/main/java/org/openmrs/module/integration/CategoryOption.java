package org.openmrs.module.integration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryOption extends OpenmrsDhisObject {
	public static Log log = LogFactory.getLog(CategoryOption.class);

	private int categoryOptionId;

	private Set<Option> options=  new HashSet<Option>(0);
	private Set<CategoryCombo> categoryCombos= new HashSet<CategoryCombo>(0);
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getCategoryOptionId();
	}

	@Override
	public void setId(Integer id) {
		setCategoryOptionId(id);		
	}

	public int getCategoryOptionId() {
		return categoryOptionId;
	}

	public void setCategoryOptionId(int categoryOptionId) {
		this.categoryOptionId = categoryOptionId;
	}
	
	 public Set<Option> getOptions() {
	        return options;
	    }
	 
	    public void setOptions(Set<Option> options) {
	        this.options = options;
	    }
	    
	    public Set<CategoryCombo> getCategoryCombos() {
	        return categoryCombos;
	    }
	 
	    public void setCategoryCombos(Set<CategoryCombo> categoryCombos) {
	        this.categoryCombos = categoryCombos;
	    }
	
	public void setIntegrationServer(IntegrationServer integrationServer)
    {
        this.integrationServer = integrationServer;
    }

    public IntegrationServer getIntegrationServer()
    {
        return this.integrationServer;
    }
}