package com.interior.adapter.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomTypeAdapter extends TypeAdapter<Map<String, String>> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Map<String, String> value) throws IOException {
        gson.toJson(value, Map.class, out);
    }

    @Override
    public Map<String, String> read(JsonReader in) throws IOException {
        Map<String, String> map = new HashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            JsonElement element = JsonParser.parseReader(in);
            map.put(name, elementToString(element));
        }
        in.endObject();
        return map;
    }

    private String elementToString(JsonElement element) {
        if (element.isJsonNull()) {
            return null;
        } else if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else if (element.isJsonArray() || element.isJsonObject()) {
            return element.toString();
        } else {
            throw new IllegalStateException("Unexpected JSON element type: " + element.getClass());
        }
    }
}
