package com.dstar.imate.data;

import com.eaio.uuid.UUID;
import com.google.gson.annotations.Expose;

public class StringData implements IData {
	private static final long serialVersionUID = 1L;

	@Expose
	private String val;
	
	@Expose
	private UUID uuid;

	public StringData(UUID id, String value) {
		this.uuid = id;
		this.val = value;
	}

	public StringData(String value) {
		this(null, value);
	}

	public StringData() {
		this(null, null);
	}

	public String getValue() {
		return val;
	}

	public void setValue(String value) {
		this.val = value;
	}

	@Override
	public UUID getId() {
		return uuid;
	}

	@Override
	public String toString(){
		return this.val;
	}
	
}
