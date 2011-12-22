package com.dstar.imate.web.ws.base.data;

import com.dstar.imate.transport.Response;

public class JsonRequest implements IJsonInteraction {
	private static final long serialVersionUID = 1L;
	String type; //null means normal request, not a PROTOCOL request
	String callback;
	String operation;
	Response request;
	public JsonRequest() {
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
	public Response getRequest() {
		return request;
	}
	public void setRequest(Response request) {
		this.request = request;
	}
	@Override
	public String toString() {
		return "JsonRequest [type=" + type + ", callback=" + callback + ", operation=" + operation + ", request=" + request + "]";
	} 
}
