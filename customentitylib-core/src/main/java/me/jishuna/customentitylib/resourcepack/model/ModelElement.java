package me.jishuna.customentitylib.resourcepack.model;

import java.util.EnumMap;
import java.util.Map;
import org.joml.Vector3f;
import me.jishuna.customentitylib.Utils;
import me.jishuna.customentitylib.model.Face;

public class ModelElement {
    public final Vector3f from;
    public final Vector3f to;
    public final ElementRotation rotation;
    public final Map<Face, ModelFace> faces;

    public ModelElement() {
        this(null, null, null, null);
    }

    public ModelElement(Vector3f from, Vector3f to, ElementRotation rotation, Map<Face, ModelFace> faces) {
        this.from = Utils.getOr(from, Vector3f::new);
        this.to = Utils.getOr(to, Vector3f::new);
        this.rotation = Utils.getOr(rotation, ElementRotation::new);
        this.faces = Utils.getOr(faces, () -> new EnumMap<>(Face.class));
    }

    public Vector3f getFrom() {
        return new Vector3f(this.from);
    }

    public Vector3f getTo() {
        return new Vector3f(this.to);
    }
}
