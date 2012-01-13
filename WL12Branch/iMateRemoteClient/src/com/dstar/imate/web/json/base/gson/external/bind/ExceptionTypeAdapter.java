package com.dstar.imate.web.json.base.gson.external.bind;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.MiniGson;
import com.google.gson.internal.bind.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public final class ExceptionTypeAdapter extends TypeAdapter<Throwable> {
	public static final Factory FACTORY = new Factory() {
		@SuppressWarnings("unchecked")
		// we use a runtime check to make sure the 'T's equal
		public <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> typeToken) {
			return (typeToken.getRawType() == java.lang.Throwable.class || typeToken.getRawType() == java.lang.Exception.class) ? (TypeAdapter<T>) new ExceptionTypeAdapter() : null;
		}
	};

	@Override
	public Throwable read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		try {
			return new Exception(reader.nextString());
		} catch (Exception e) {
			throw new JsonSyntaxException(e);
		}
	}

	@Override
	public void write(JsonWriter writer, Throwable value) throws IOException {
		writer.value(value != null ? getDeepestCause(value) : null); // Not persisting stack, as not de-serializable.
	}
	
	private static final String getDeepestCause(Throwable t){
		if(t==null){
			return "";
		}else if(t.getCause()==null){
			return t.getMessage()!=null?t.getMessage():"";
		}else{//t!=null && t.getCause() !=null
			return (t.getMessage()!=null?t.getMessage():"")+"["+getDeepestCause(t.getCause())+"]";
		}
	}
}
