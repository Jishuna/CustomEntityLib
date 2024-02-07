package me.jishuna.customentitylib.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public class Vector4fAdapter extends TypeAdapter<Vector4fc> {

    @Override
    public void write(JsonWriter out, Vector4fc value) throws IOException {
        out.beginArray();
        out.value(value.x());
        out.value(value.y());
        out.value(value.z());
        out.value(value.w());
        out.endArray();
    }

    @Override
    public Vector4fc read(JsonReader in) throws IOException {
        in.beginArray();
        float x = (float) in.nextDouble();
        float y = (float) in.nextDouble();
        float z = (float) in.nextDouble();
        float w = (float) in.nextDouble();
        in.endArray();

        return new Vector4f(x, y, z, w);
    }
}
