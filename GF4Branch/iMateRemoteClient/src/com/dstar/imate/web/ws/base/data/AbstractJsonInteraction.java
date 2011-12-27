package com.dstar.imate.web.ws.base.data;

import com.dstar.imate.transport.Response;

public class AbstractJsonInteraction implements IJsonInteraction {
	private static final long serialVersionUID = 1L;
	String type; //null means normal request, not a PROTOCOL request
	String callback;
	String operation;
	Response data;
	public AbstractJsonInteraction() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Response getData() {
		return data;
	}
	public void setData(Response ResponseData) {
		this.data = ResponseData;
	}
}
