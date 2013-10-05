package org.openmrs.module.integration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Location;

public class ChangeRecord {
	public static Log log = LogFactory.getLog(ChangeRecord.class);

	public String objClass;
	public Integer id;
	public String name;
	public String code;
	public String uid;
	public ChangeType change;
	public String oldName;
	public String oldCode;
	public String newFreq;
	
	public enum ChangeType {ADD, DELETE, CHANGE, REVISE};
}