package me.jishuna.customentitylib.test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.joml.Vector3f;

public class AnimatorDeserializer implements JsonDeserializer<Animator> {

    @Override
    public Animator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return readAnimator(json.getAsJsonObject(), context);
    }

    private Animator readAnimator(JsonObject json, JsonDeserializationContext context) {
        List<Keyframe> positions = new ArrayList<>();
        List<Keyframe> rotations = new ArrayList<>();
        List<Keyframe> scales = new ArrayList<>();

        json.getAsJsonArray("keyframes").forEach(value -> {
            int time = Math.round(value.getAsJsonObject().get("time").getAsFloat() * 20);
            Vector3f vector = readVector(value.getAsJsonObject().getAsJsonArray("data_points").get(0).getAsJsonObject());

            Keyframe keyframe = new Keyframe(time, vector);

            switch (value.getAsJsonObject().get("channel").getAsString()) {
            case "position" -> {
                keyframe.getValue().div(-16, 16, -16);
                positions.add(keyframe);
            }
            case "rotation" -> {
                keyframe.getValue().mul(BBModelParser.DEGREES_TO_RADIANS, -BBModelParser.DEGREES_TO_RADIANS, BBModelParser.DEGREES_TO_RADIANS);
                rotations.add(keyframe);
            }
            case "scale" -> scales.add(keyframe);
            }
        });

        positions.sort(Comparator.comparing(Keyframe::getTime));
        rotations.sort(Comparator.comparing(Keyframe::getTime));
        scales.sort(Comparator.comparing(Keyframe::getTime));

        return new Animator(new AnimationChannel(positions.toArray(Keyframe[]::new)),
                new AnimationChannel(rotations.toArray(Keyframe[]::new)),
                new AnimationChannel(scales.toArray(Keyframe[]::new)));
    }

    private Vector3f readVector(JsonObject json) {
        float x = json.get("x").getAsFloat();
        float y = json.get("y").getAsFloat();
        float z = json.get("z").getAsFloat();

        return new Vector3f(x, y, z);
    }

}
