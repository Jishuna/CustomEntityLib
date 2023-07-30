package me.jishuna.cel;

import java.util.Map;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

public class Bone {
    private final String name;
    private final Vector3f position;
    private final Vector3f rotation;
    private final int customModelData;
    private final Map<String, Bone> children;

    public Bone(String name, Vector3f position, Vector3f rotation, int customModelData, Map<String, Bone> children) {
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        this.customModelData = customModelData;
        this.children = ImmutableMap.copyOf(children);
    }

    public String getName() {
        return name;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Map<String, Bone> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String prefix) {
        StringBuilder builder = new StringBuilder(prefix).append("Name: ").append(this.name).append("\n");

        builder.append(prefix).append("Position: ").append(this.position).append("\n");
        if (!this.children.isEmpty()) {
            builder.append(prefix).append("Children: ").append("\n");
            this.children.values().forEach(bone -> builder.append(bone.toString(prefix + "  ")));
        }

        return builder.toString();
    }

}
