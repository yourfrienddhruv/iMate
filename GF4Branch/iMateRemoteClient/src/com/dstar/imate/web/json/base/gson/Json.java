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

	public static String to(Object obj) {
		init();
		return gson.toJson(obj);
	}

	/**
	 * Use for generics You can solve this problem by specifying the correct parameterized type for your generic type. You can do this by
	 * using the TypeToken class. Type fooType = new TypeToken<Foo<Bar>>() {}.getType();
	 * 
	 * gson.toJson(foo, fooType);
	 * gson.fromJson(json, fooType);
	 * @param json 
	 * @param classOfT Foo.class
	 * @return Object deserialized from JSONString
	 * @throws JsonSyntaxException 
	 */
	public static <T> T from(String json, Type classOfT) throws JsonSyntaxException {
		init();
		return gson.fromJson(json,classOfT);
	}

}
