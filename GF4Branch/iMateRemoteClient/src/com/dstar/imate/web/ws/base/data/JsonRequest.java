package com.dstar.imate.web.ws.base.data;

import com.dstar.imate.data.IData;


public class JsonRequest<DataClass extends IData> extends AbstractJsonInteraction<DataClass> {
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "JsonRequest [type=" + type + ", data=" + data + "]";
	} 
}
