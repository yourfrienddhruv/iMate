/**
 * 
 */
package com.dstar.imate.web.json.base.gson.external.bind;

import java.lang.reflect.Type;

import com.dstar.imate.data.IData;
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
public class JsonRequestDefaultToStringDataDeserializer implements JsonDeserializer<JsonRequest<? extends IData>> {
	public JsonRequest<StringData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonRequest<StringData> request = new JsonRequest<StringData>();
		JsonObject _JsonRequest = json.getAsJsonObject();
		request.setType(_JsonRequest.get("type").getAsString());
		ResponseData<StringData> data;

		JsonObject _ResponseData = _JsonRequest.get("data").getAsJsonObject();
		StringData stringData = new StringData(null, _ResponseData.get("data").getAsString());
		StringData[] stringDataSet = new StringData[1];
		stringDataSet[0] = new StringData(null, _ResponseData.get("dataSet").getAsString());

		if (_ResponseData.get("success").isJsonNull()// not passed so considering failure
				|| _ResponseData.get("success").getAsBoolean()) {// positive
			data = ResponseData.positive(stringData, stringDataSet);
		} else {// passed false so considering negative
			String messageKey = _ResponseData.get("messageKey").getAsString();
			data = ResponseData.negative(messageKey, stringData, stringDataSet);
		}
		request.setData(data);
		return request;
	}
}
