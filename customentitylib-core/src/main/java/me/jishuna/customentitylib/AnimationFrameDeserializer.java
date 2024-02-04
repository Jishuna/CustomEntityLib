package me.jishuna.customentitylib;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimationFrameDeserializer implements JsonDeserializer<AnimationFrame> {

    @Override
    public AnimationFrame deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<UUID, Pose> poses = new HashMap<>();

        json.getAsJsonObject().getAsJsonArray("nodes").forEach(entry -> {
            UUID id = context.deserialize(entry.getAsJsonObject().get("uuid"), UUID.class);
            Pose pose = context.deserialize(entry, Pose.class);

            poses.put(id, pose);
        });

        return new AnimationFrame(poses);
    }
}
