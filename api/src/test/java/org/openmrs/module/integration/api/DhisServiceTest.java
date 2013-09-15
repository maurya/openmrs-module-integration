package org.openmrs.module.integration.api;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.integration.OrgUnit;
import org.openmrs.module.integration.UndefinedCohortDefinition;
import org.openmrs.module.integration.UndefinedCohortDefinitionEvaluator;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.annotation.Rollback;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DhisServiceTest extends BaseModuleContextSensitiveTest {

	private DhisService ds;

	@Before
	public void setup() {
		ds=Context.getService(DhisService.class);
	}

	@Rollback(true)
	@Test
	public void UndefinedCohortDefinition_shouldAddCohortDefOnlyIfNecessary() {
		CohortDefinitionService cds = Context.getService(CohortDefinitionService.class);
		List<CohortDefinition> defs = cds.getAllDefinitions(true);
		int nBefore=0;
		for (CohortDefinition cd : defs) {
			if (cd instanceof UndefinedCohortDefinition) {
				nBefore++;
			}
		}
		CohortDefinition undefined = ds.getUndefinedCohortDefinition();
		defs = cds.getAllDefinitions(true);
		int nAfter=0;
		for (CohortDefinition cd : defs) {
			if (cd instanceof UndefinedCohortDefinition) {
				nAfter++;
			}
		}

		if (nBefore==0 && nAfter==0) {
			Assert.assertEquals("Undefined cohort def was not created", nAfter,1);
		} else if (nBefore==0) {
			Assert.assertEquals("More than one undefined cohort def was created", nAfter, 1);
			CohortDefinition undef2 = ds.getUndefinedCohortDefinition();
			defs = cds.getAllDefinitions(true);
			nAfter=0;
			for (CohortDefinition cd : defs) {
				if (cd instanceof UndefinedCohortDefinition) {
					nAfter++;
				}
			}
			Assert.assertEquals("More than one undefined cohort def was created", nAfter, 1);
			Assert.assertEquals("Same object should always be returned", undefined, undef2);
		} else {
			Assert.assertEquals("No undefined cohort def should be created", nAfter, nBefore);
		}
		Assert.assertNotNull("Undefined cohort def is null",undefined);
	}

	@Test
	public void UndefinedCohortDefinition_shouldEvaluateToEmptyCohort() throws EvaluationException {
		CohortDefinition undef = ds.getUndefinedCohortDefinition();
		EvaluationContext ec = new EvaluationContext();
		CohortDefinitionEvaluator cde = new UndefinedCohortDefinitionEvaluator();
		EvaluatedCohort c = cde.evaluate(undef,ec);

		Assert.assertEquals("Should evaluate to an empty cohort",c.getMemberIds().size(),0);
	}

	@Test
	public void Uid_shouldBe11CharsEvenIfNull() {
		OrgUnit o = new OrgUnit("",null,"");
		Assert.assertEquals("Uid should be 11 chars", 11, o.getUid().length());
		Assert.assertEquals("Code should equal uid",o.getUid(), o.getCode());
		Assert.assertEquals("Name should be set to code", o.getCode(), o.getName());
	}

}