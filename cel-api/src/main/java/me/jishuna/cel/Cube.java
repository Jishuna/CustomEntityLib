package me.jishuna.cel;

import java.util.Map;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import team.unnamed.creative.base.CubeFace;
import team.unnamed.creative.model.ElementFace;
import team.unnamed.creative.model.ElementRotation;

public class Cube {
    private final Vector3f from;
    private final Vector3f to;
    private final ElementRotation rotation;
    private final Map<CubeFace, ElementFace> faces;

    public Cube(Vector3f from, Vector3f to, ElementRotation rotation, Map<CubeFace, ElementFace> faces) {
        this.from = from;
        this.to = to;
        this.rotation = rotation;
        this.faces = ImmutableMap.copyOf(faces);
    }

    public Vector3f getFrom() {
        return from;
    }

    public Vector3f getTo() {
        return to;
    }

    public ElementRotation getRotation() {
        return rotation;
    }

    public Map<CubeFace, ElementFace> getFaces() {
        return faces;
    }
}
