package me.jishuna.customentitylib.test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimationDeserializer implements JsonDeserializer<Animation> {

    @Override
    public Animation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return readAnimation(json.getAsJsonObject(), context);
    }

    private Animation readAnimation(JsonObject json, JsonDeserializationContext context) {
        Map<UUID, Animator> animators = new HashMap<>();
        int length = Math.round(json.get("length").getAsFloat() * 20);

        json.getAsJsonObject("animators").asMap().forEach((id, value) -> {
            UUID uuid = UUID.fromString(id);
            Animator animator = BBModelParser.GSON.fromJson(value, Animator.class);

            animators.put(uuid, animator);
        });

        return new Animation(length, animators);
    }

}
