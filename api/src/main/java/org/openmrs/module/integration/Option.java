package org.openmrs.module.integration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class Option extends OpenmrsDhisObject {
	public static Log log = LogFactory.getLog(Option.class);

	private int optionId;
	private String cohortdefUuid;
	private Set<CategoryOption> categoryOptions=  new HashSet<CategoryOption>(0);
	private Set<OptionSet> optionSets=  new HashSet<OptionSet>(0);
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getOptionId();
	}

	@Override
	public void setId(Integer id) {
		setOptionId(id);		
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	
	public String getCohortdefUuid() {
		return cohortdefUuid;
	}

	public void setCohortdefUuid(String cohortdefUuid) {
		this.cohortdefUuid = cohortdefUuid;
	}
	
	 public Set<CategoryOption> getCategoryOptions() {
	        return categoryOptions;
	    }
	 
	    public void setCategoryOptions(Set<CategoryOption> categoryOptions) {
	        this.categoryOptions = categoryOptions;
	    }
	
	 public Set<OptionSet> getOptionSets() {
	        return optionSets;
	    }
	 
	    public void setOptionSets(Set<OptionSet> optionSets) {
	        this.optionSets = optionSets;
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