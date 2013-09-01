package org.openmrs.module.integration;

import java.util.Date;
import java.util.List;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.OpenmrsObject;
import org.openmrs.User;
import org.openmrs.module.reporting.cohort.definition.BaseCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class UndefinedCohortDefinition extends BaseCohortDefinition {

    public static final long serialVersionUID = 1L;
	
	//***** CONSTRUCTORS *****

	/**
	 * Default Constructor
	 */
	public UndefinedCohortDefinition() {
		super();
		super.setName("Undefined");
		super.setDescription("Undefined cohort definition for DHIS2 integration");
		super.setRetired(true);
	}
	
	//***** INSTANCE METHODS *****
	

	@Override
	public void setDescription(String arg0) {
	}

	@Override
	public void setName(String arg0) {
	}

	@Override
	public void setRetired(Boolean arg0) {
	}

	@Override
	public void addParameter(Parameter parameter) {
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Undefined";
	}

}
