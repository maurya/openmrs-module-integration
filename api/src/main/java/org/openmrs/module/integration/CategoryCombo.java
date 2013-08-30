package org.openmrs.module.integration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class CategoryCombo extends BaseOpenmrsObject {
	public static Log log = LogFactory.getLog(CategoryCombo.class);

	private int categoryComboId;
	private String name;
	private String code;
	private String uid;
	private Set<CategoryOption> categoryOptions=  new HashSet<CategoryOption>(0);
	private Set<OptionSet> optionSets=  new HashSet<OptionSet>(0);
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getCategoryComboId();
	}

	@Override
	public void setId(Integer id) {
		setCategoryComboId(id);		
	}

	public int getCategoryComboId() {
		return categoryComboId;
	}

	public void setCategoryComboId(int categoryComboId) {
		this.categoryComboId = categoryComboId;
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