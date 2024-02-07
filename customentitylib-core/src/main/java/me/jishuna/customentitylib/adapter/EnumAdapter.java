package me.jishuna.customentitylib.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumAdapter extends TypeAdapter<Object> {
    private final Map<String, Object> lookupMap;

    public EnumAdapter(Object[] enumConstants) {
        this.lookupMap = Arrays.stream(enumConstants).collect(Collectors.toMap(Object::toString, Function.identity()));
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        out.value(value.toString().toLowerCase());
    }

    @Override
    public Object read(JsonReader in) throws IOException {
        return this.lookupMap.get(in.nextString().toUpperCase());
    }

}
