package me.jishuna.customentitylib.model;

import java.util.UUID;
import org.joml.Matrix4f;
import me.jishuna.customentitylib.resourcepack.model.ModelElement;

public class Bone {

    private final UUID id;
    private final String name;
    private final Matrix4f matrix;
    private final ModelElement[] cubes;
    private final Bone[] children;
    private final int customModelData;

    public Bone(UUID id, String name, Matrix4f matrix, ModelElement[] cubes, Bone[] children, int customModelData) {
        this.id = id;
        this.name = name.toLowerCase();
        this.matrix = matrix;
        this.cubes = cubes;
        this.children = children;
        this.customModelData = customModelData;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Matrix4f getTransform() {
        return this.matrix;
    }

    public ModelElement[] getCubes() {
        return this.cubes;
    }

    public Bone[] getChildren() {
        return this.children;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }
}
