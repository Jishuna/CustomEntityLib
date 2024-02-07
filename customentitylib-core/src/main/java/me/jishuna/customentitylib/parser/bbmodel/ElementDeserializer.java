package me.jishuna.customentitylib.parser.bbmodel;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import org.joml.Vector3f;
import me.jishuna.customentitylib.model.Axis;
import me.jishuna.customentitylib.model.Face;
import me.jishuna.customentitylib.resourcepack.model.ElementRotation;
import me.jishuna.customentitylib.resourcepack.model.ModelElement;
import me.jishuna.customentitylib.resourcepack.model.ModelFace;

public class ElementDeserializer implements JsonDeserializer<ModelElement> {

    @Override
    public ModelElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return readCube(json.getAsJsonObject(), context);
    }

    private ModelElement readCube(JsonObject json, JsonDeserializationContext context) {
        Vector3f from = new Vector3f((float[]) context.deserialize(json.get("from"), float[].class));
        Vector3f to = new Vector3f((float[]) context.deserialize(json.get("to"), float[].class));
        Vector3f pivot = new Vector3f((float[]) context.deserialize(json.get("origin"), float[].class));

        if (json.has("inflate")) {
            float inflate = json.get("inflate").getAsFloat() / 2;
            from.sub(inflate, inflate, inflate);
            to.add(inflate, inflate, inflate);
        }

        Vector3f rotation = new Vector3f();
        if (json.has("rotation")) {
            rotation.set((float[]) context.deserialize(json.get("rotation"), float[].class));
        }

        float x = rotation.x();
        float y = rotation.y();
        float z = rotation.z();

        Axis axis;
        float angle;

        if (x != 0) {
            axis = Axis.X;
            angle = x;
        } else if (y != 0) {
            axis = Axis.Y;
            angle = y;
        } else {
            axis = Axis.Z;
            angle = z;
        }

        ElementRotation rot = new ElementRotation(angle, axis, pivot);
        return new ModelElement(from, to, rot, readFaces(json.getAsJsonObject("faces"), context));
    }

    private Map<Face, ModelFace> readFaces(JsonObject json, JsonDeserializationContext context) {
        Map<Face, ModelFace> faces = new EnumMap<>(Face.class);

        json.asMap().forEach((key, value) -> {
            Face face = Face.valueOf(key.toUpperCase());
            ModelFace cubeFace = context.deserialize(value, ModelFace.class);
            cubeFace.texture = "#" + cubeFace.texture;
            cubeFace.uv.div(4);
            faces.put(face, cubeFace);
        });

        return faces;
    }

}
