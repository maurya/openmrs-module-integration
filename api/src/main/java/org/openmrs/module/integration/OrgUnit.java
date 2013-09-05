package org.openmrs.module.integration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

public class OrgUnit extends OpenmrsDhisObject {
	public static Log log = LogFactory.getLog(OrgUnit.class);

	private int orgUnitId;
	private Set<OrgUnit> childOrgs;
	private IntegrationServer integrationServer;

	@Override
	public Integer getId() {
		return getOrgUnitId();
	}

	@Override
	public void setId(Integer id) {
		setOrgUnitId(id);		
	}

	public int getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(int orgUnitId) {
		this.orgUnitId = orgUnitId;
	}
	
	public Set<OrgUnit> getChildOrgs() {
		if (childOrgs==null) {
			childOrgs=new HashSet<OrgUnit>();
		}
		return childOrgs;
	}

	public void setChildOrgs(Set<OrgUnit> childOrgs) {
		this.childOrgs = childOrgs;
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