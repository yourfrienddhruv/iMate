package com.dstar.imate.web.json.base.gson.external.bind;

import java.io.IOException;

import com.eaio.uuid.UUID;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.MiniGson;
import com.google.gson.internal.bind.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public final class UUIDTypeAdapter extends TypeAdapter<UUID> {
	public static final Factory FACTORY = new Factory() {
		@SuppressWarnings("unchecked")
		// we use a runtime check to make sure the 'T's equal
		public <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> typeToken) {
			return typeToken.getRawType() == UUID.class ? (TypeAdapter<T>) new UUIDTypeAdapter() : null;
		}
	};

	@Override
	public UUID read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		try {
			String [] tokens= reader.nextString().split("_");
			return new UUID(Long.parseLong(tokens[0]), Long.parseLong(tokens[1]));
		} catch (Exception e) {
			throw new JsonSyntaxException(e);
		}
	}

	@Override
	public void write(JsonWriter writer, UUID value) throws IOException {
		writer.value(value != null ? value.getTime()+"_"+value.getClockSeqAndNode() : null);
	}
}
