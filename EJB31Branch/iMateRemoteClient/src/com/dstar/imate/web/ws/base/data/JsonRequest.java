package com.dstar.imate.web.ws.base.data;

import java.lang.reflect.Type;

import com.dstar.imate.data.IData;
import com.dstar.imate.data.StringData;
import com.dstar.imate.web.json.base.gson.Json;

public class JsonRequest extends AbstractJsonInteraction<StringData> {
	private static final long serialVersionUID = 1L;
	public static final Type TYPE_JsonRequest_partialProcessingDataAttribute  = JsonRequest.class; 
	
	public <dataClass extends IData> dataClass getData(Class<dataClass> clazz){
		if(this.getData()==null || this.getData().getData()==null || this.getData().getData().getVal()==null){
			return null;
		}
		return Json.from(this.getData().getData().getVal(),clazz);
	}
	
	public <dataClass extends IData> dataClass[] getDataSet(Class<dataClass[]> arrayClazz){
		if(this.getData()==null || this.getData().getDataSet()==null || this.getData().getDataSet().length<1 || this.getData().getDataSet()[0]==null){
			return null;
		}
		return Json.from(this.getData().getDataSet()[0].getVal(),arrayClazz);
	}
	
	@Override
	public String toString() {
		return "JsonRequest [type=" + getType() + ", data=" + getData() + "]";
	}
	
	public static JsonRequest fromJson(String json){
		// This will do partial decoding except data.data Field as data<StringData>.value="{jsonObjectRepresentation}"
		return Json.from(json, TYPE_JsonRequest_partialProcessingDataAttribute); 
		
		//iF wants to directly parse whole object tree then supply this 
		//final Type TYPE_JsonRequest_Coupon = new com.google.gson.reflect.TypeToken<JsonRequest<CouponEntity>>() {}.getType();
		/// also have unregistor to JsonRequestDefaultToStringDataDeserializer from Gson
	}
}
