package org.openmrs.module.integration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.openmrs.BaseOpenmrsObject;

public abstract class OpenmrsDhisObject extends BaseOpenmrsObject {
	
	private String name;
	private String code;
	private String uid;
    private SimpleDateFormat LONG_DATE_FORMAT;
	
	public OpenmrsDhisObject() {
		super();
		LONG_DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
	}
	
	public OpenmrsDhisObject(String dhisName, String dhisCode, String dhisUid) {
		super();
		LONG_DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );

		if (isNullOrEmpty(dhisCode) && isNullOrEmpty(dhisUid)) {
			this.setUid(uuidToBase64(UUID.fromString(this.getUuid())));
			this.setCode(this.getUid());
		} else if (isNullOrEmpty(dhisCode)) {
			this.setUid(dhisUid);
			this.setCode(dhisUid);
		} else if (isNullOrEmpty(dhisUid)) {
			this.setUid(uuidToBase64(UUID.fromString(this.getUuid())));
			this.setCode(dhisCode);
		} else {
			this.setUid(dhisUid);
			this.setCode(dhisCode);
		}

		if (isNullOrEmpty(dhisName)) {
			this.setName(this.getCode());
		} else {
			this.setName(dhisName);
		}
	}
	
	private Boolean isNullOrEmpty(String s) {
		if (s==null)
			return true;
		else if (s.length()==0)
			return true;
		return false;
	}
	
	private String uuidToBase64(UUID uuid) {
 		 byte[] bytes = new byte[16];
		 ByteBuffer bb = ByteBuffer.wrap(bytes);
		 bb.order(ByteOrder.BIG_ENDIAN);
		 bb.putLong(uuid.getMostSignificantBits());
		 bb.putLong(uuid.getLeastSignificantBits());
		 
		 Base64 b64=new Base64();
		 return b64.encodeToString(bytes);
	}

	public Date dateFromDhis(String s) throws ParseException {
		return LONG_DATE_FORMAT.parse(s);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code=code;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid=uid;
	}
	
}
