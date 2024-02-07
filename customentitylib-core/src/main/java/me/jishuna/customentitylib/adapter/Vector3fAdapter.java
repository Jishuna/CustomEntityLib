package me.jishuna.customentitylib.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Vector3fAdapter extends TypeAdapter<Vector3fc> {

    @Override
    public void write(JsonWriter out, Vector3fc value) throws IOException {
        out.beginArray();
        out.value(value.x());
        out.value(value.y());
        out.value(value.z());
        out.endArray();
    }

    @Override
    public Vector3fc read(JsonReader in) throws IOException {
        in.beginArray();
        float x = (float) in.nextDouble();
        float y = (float) in.nextDouble();
        float z = (float) in.nextDouble();
        in.endArray();

        return new Vector3f(x, y, z);
    }
}
