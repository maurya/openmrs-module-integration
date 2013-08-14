package org.openmrs.module.integration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryCombo extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(CategoryCombo.class);

	private int categoryOptionId;
	private String name;
	private String code;
	private String uid;
	private Set<CategoryOption> categoryOptions=  new HashSet<CategoryOption>();
	private Set<OptionSet> optionSets=  new HashSet<OptionSet>();
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