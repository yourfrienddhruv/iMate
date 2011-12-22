package com.dstar.imate.web.json.base.gson;

import java.lang.reflect.Type;

import com.dstar.imate.web.json.base.gson.external.bind.ExceptionTypeAdapter;
import com.dstar.imate.web.json.base.gson.external.bind.UUIDTypeAdapter;
import com.eaio.uuid.UUID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Json {
	private static Gson gson;

	public static void init() {
		if (gson == null) {
			GsonBuilder gsonB = new GsonBuilder();
			gsonB.registerTypeAdapter(Exception.class, ExceptionTypeAdapter.FACTORY);
			gsonB.registerTypeAdapter(UUID.class, UUIDTypeAdapter.FACTORY);
			gson = gsonB.create();
		}
	}
	
	public static String to(Object obj){
		init();
		return gson.toJson(obj);
	}
	
	public static <T> T from(String json, Class<T> classOfT) throws JsonSyntaxException {
		init();
		return gson.fromJson(json, (Type) classOfT);
	}
	
}
