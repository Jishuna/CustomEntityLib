package me.jishuna.customentitylib.test;

import com.google.gson.JsonObject;
import org.joml.Vector3f;

public class CubeRotation {
    private final Vector3f pivot;
    private final Axis axis;
    private final float angle;

    public CubeRotation(Vector3f pivot, Axis axis, float angle) {
        this.pivot = pivot;
        this.axis = axis;
        this.angle = angle;
    }

    public JsonObject asJsonObject() {
        JsonObject root = new JsonObject();
        root.addProperty("angle", this.angle);
        root.addProperty("axis", this.axis.toString().toLowerCase());
        root.add("origin", BBModelParser.GSON.toJsonTree(new float[] { this.pivot.x, this.pivot.y, this.pivot.z }));

        return root;
    }

    @Override
    public String toString() {
        return "CubeRotation [pivot=" + this.pivot + ", axis=" + this.axis + ", angle=" + this.angle + "]";
    }
}
