package org.openmrs.module.integration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class Option extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(Option.class);

	private int optionId;
	private String name;
	private String code;
	private String uid;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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