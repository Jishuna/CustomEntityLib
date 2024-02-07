package me.jishuna.customentitylib.model;

import java.util.UUID;
import me.jishuna.customentitylib.BoneTransformation;
import me.jishuna.customentitylib.resourcepack.model.ModelElement;

public class Bone {

    private final UUID id;
    private final String name;
    private final BoneTransformation transformation;
    private final ModelElement[] cubes;
    private final Bone[] children;
    private final int customModelData;

    public Bone(UUID id, String name, BoneTransformation transformation, ModelElement[] cubes, Bone[] children, int customModelData) {
        this.id = id;
        this.name = name.toLowerCase();
        this.transformation = transformation;
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

    public BoneTransformation getTransformation() {
        return this.transformation;
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
