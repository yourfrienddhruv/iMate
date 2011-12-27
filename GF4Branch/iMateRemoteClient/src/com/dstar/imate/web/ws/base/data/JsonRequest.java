package com.dstar.imate.web.ws.base.data;


public class JsonRequest extends AbstractJsonInteraction {
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "JsonRequest [type=" + type + ", callback=" + callback + ", operation=" + operation + ", data=" + data + "]";
	} 
}
