package me.jishuna.cel;

import java.util.Collection;
import java.util.Map;

import team.unnamed.creative.base.Writable;

public class ModelAsset {
    private final String name;
    private final Map<String, Writable> textures;
    private final Map<Integer, String> textureMapping;
    private final Map<String, BoneAsset> bones;

    public ModelAsset(String name, Map<String, Writable> textures, Map<Integer, String> textureMapping, Map<String, BoneAsset> bones) {
        this.name = name;
        this.textures = textures;
        this.textureMapping = textureMapping;
        this.bones = bones;
    }

    public String name() {
        return name;
    }

    public Map<String, Writable> textures() {
        return textures;
    }

    public Map<Integer, String> textureMapping() {
        return textureMapping;
    }

    public Collection<BoneAsset> bones() {
        return bones.values();
    }

    public Map<String, BoneAsset> boneMap() {
        return bones;
    }
}
