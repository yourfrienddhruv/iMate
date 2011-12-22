package com.dstar.imate.data;

import com.eaio.uuid.UUID;

public class StringData implements IString {
	private static final long serialVersionUID = 1L;

	String val;
	UUID uuid;

	public StringData(UUID id, String value) {
		this.uuid = id;
		this.val = value;
	}

	public StringData(String value) {
		this(new UUID(), value);
	}

	public StringData() {
		this(new UUID(), null);
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
