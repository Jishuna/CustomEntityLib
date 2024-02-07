package me.jishuna.customentitylib.model;

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

    public Vector3f getPivot() {
        return this.pivot;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public float getAngle() {
        return this.angle;
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
