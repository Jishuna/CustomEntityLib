package me.jishuna.customentitylib.resourcepack.model;

import org.joml.Vector3f;
import me.jishuna.customentitylib.Utils;
import me.jishuna.customentitylib.model.Axis;

public class ElementRotation {
    public float angle;
    public Axis axis;
    public final Vector3f origin;

    public ElementRotation() {
        this(null, null, null);
    }

    public ElementRotation(Float angle, Axis axis, Vector3f origin) {
        this.angle = Utils.getOr(angle, () -> 0f);
        this.axis = Utils.getOr(axis, () -> Axis.Y);
        this.origin = Utils.getOr(origin, Vector3f::new);
    }

    public Vector3f getOrigin() {
        return new Vector3f(this.origin);
    }
}
