package me.jishuna.customentitylib.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Base64;
import me.jishuna.customentitylib.resourcepack.Texture;

public class TextureDeserializer implements JsonDeserializer<Texture> {
    private static final String BASE_64_PREFIX = "data:image/png;base64,";

    @Override
    public Texture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return readTexture(json.getAsJsonObject(), context);
    }

    private Texture readTexture(JsonObject json, JsonDeserializationContext context) {
        String id = json.get("id").getAsString();
        String name = json.get("name").getAsString();
        String source = json.get("source").getAsString();

        byte[] data = Base64.getDecoder().decode(source.substring(BASE_64_PREFIX.length()));

        return new Texture(id, name, data);
    }
}
