/**
 * 
 */
package com.dstar.imate.web.json.base.gson.external.bind;

import java.lang.reflect.Type;

import com.dstar.imate.data.StringData;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.ws.base.data.JsonRequest;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author Dhruv Gohil 
 * By Default deserialize to JsonRequest for StringData so evaluation of JsonRequest.data.data could be postponed.
 * 
 */
public class JsonRequestDefaultToStringDataDeserializer implements JsonDeserializer<JsonRequest> {
	public static final JsonDeserializer<JsonRequest> FACTORY = new JsonRequestDefaultToStringDataDeserializer();

	public JsonRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonRequest request = new JsonRequest();
		JsonObject _JsonRequest = json.getAsJsonObject();
		request.setType(_JsonRequest.get("type").getAsString());
		ResponseData<StringData> data;

		JsonObject _ResponseData = _JsonRequest.get("data").getAsJsonObject();
		StringData stringData =null;
		if( _ResponseData.has("data") && !_ResponseData.get("data").isJsonNull()){
			//although its object we are taking as string to postpone parsing.
			stringData = new StringData(null,_ResponseData.get("data").getAsJsonObject().toString());
		}
		
		StringData[] stringDataSet =null;
		if( _ResponseData.has("dataSet") && !_ResponseData.get("dataSet").isJsonNull()){
			//although its object[] we are taking as string to postpone parsing.
			stringDataSet = new StringData[1];
			stringDataSet[0] = new StringData(null, _ResponseData.get("dataSet").getAsJsonArray().toString());
		}
		
		if (_ResponseData.has("success") && !_ResponseData.get("success").getAsBoolean()) {// success="false" NEGATIVE 
			String messageKey = _ResponseData.get("messageKey").getAsString();
			data = ResponseData.negative(messageKey, stringData, stringDataSet);
		} else {// if missing or has success="true" then consider POSITIVE
			data = ResponseData.positive(stringData, stringDataSet);
		}
		request.setData(data);
		return request;
	}
}
