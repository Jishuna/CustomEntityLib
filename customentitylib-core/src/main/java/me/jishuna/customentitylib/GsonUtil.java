package me.jishuna.customentitylib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joml.Vector3f;
import org.joml.Vector4f;
import me.jishuna.customentitylib.adapter.CustomTypeAdapterFactory;
import me.jishuna.customentitylib.adapter.Vector3fAdapter;
import me.jishuna.customentitylib.adapter.Vector4fAdapter;

public class GsonUtil {
    public static final Gson GSON = createGson();

    private static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        builder.setPrettyPrinting();

        builder.registerTypeAdapterFactory(CustomTypeAdapterFactory.INSTANCE);

        builder.registerTypeAdapter(Vector3f.class, new Vector3fAdapter());
        builder.registerTypeAdapter(Vector4f.class, new Vector4fAdapter());

        return builder.create();
    }

    private GsonUtil() {
    }
}
