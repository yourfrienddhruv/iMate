package com.dstar.imate.web.ws.base.data;

import com.dstar.imate.data.IData;
import com.dstar.imate.transport.ResponseData;
import com.google.gson.annotations.Expose;

public class AbstractJsonInteraction<DataClass extends IData> implements IJsonInteraction {
	private static final long serialVersionUID = 1L;
	@Expose
	private String type; //null means normal request, not a PROTOCOL request
	
	@Expose
	private ResponseData<DataClass> data;
	
	public AbstractJsonInteraction() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ResponseData<DataClass> getData() {
		return data;
	}
	public void setData(ResponseData<DataClass> responseData) {
		this.data = responseData;
	}
}
