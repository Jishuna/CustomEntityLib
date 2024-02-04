package me.jishuna.customentitylib.test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import org.joml.Vector4f;

public class CubeFaceDeserializer implements JsonDeserializer<CubeFace> {

    @Override
    public CubeFace deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int texture = 0;
        Vector4f uv = new Vector4f((float[]) context.deserialize(json.getAsJsonObject().get("uv"), float[].class));

        return new CubeFace(uv, texture);
    }
}
