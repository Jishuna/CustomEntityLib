package me.jishuna.customentitylib.resourcepack.model;

import org.joml.Vector3f;
import me.jishuna.customentitylib.Utils;

public class ModelDisplay {
    public final Vector3f rotation;
    public final Vector3f scale;

    public ModelDisplay() {
        this(null, null);
    }

    public ModelDisplay(Vector3f rotation, Vector3f scale) {
        this.rotation = Utils.getOr(rotation, Vector3f::new);
        this.scale = Utils.getOr(scale, Vector3f::new);
    }
}
