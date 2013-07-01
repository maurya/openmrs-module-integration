/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.integration.web.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Person;
import org.openmrs.api.PatientSetService.TimeModifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.query.service.CohortQueryService;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class  IntegrationManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/integration/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		List<Person> providerList = Collections.singletonList(new Person(101-1));
		CohortQueryService cqs= Context.getService(CohortQueryService.class);
		//Cohort cohort = cqs.getPatientsHavingEncounters(null,null, TimeQualifier.ANY, null, null, null, null, null, null, null, null, null);
		Cohort cohort = cqs.getPatientsWithGender(true, false, false);
		String s=cohort.getCommaSeparatedPatientIds()+"";
		model.addAttribute("patients",s);
		model.addAttribute("count",cohort.getSize());
	}
}
