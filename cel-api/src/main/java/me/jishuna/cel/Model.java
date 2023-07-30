package me.jishuna.cel;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Model {
    private final String name;
    private final ModelAsset asset;
    private final Map<String, Bone> bones;
    // private final Map<String, Animation> animations;

    public Model(String name, ModelAsset asset, Map<String, Bone> bones/* , Map<String, Animation> animations */) {
        this.name = name;
        this.asset = asset;
        this.bones = ImmutableMap.copyOf(bones);
        // this.animations = ImmutableMap.copyOf(animations);
    }

    public String getName() {
        return this.name;
    }

    public ModelAsset getAsset() {
        return this.asset;
    }

    public Collection<Bone> getBones() {
        return this.bones.values();
    }

    public Bone getBone(String name) {
        return this.bones.get(name);
    }

//    public Collection<Animation> getAnimations() {
//        return this.animations.values();
//    }
//
//    public Animation getAnimation(String name) {
//        return this.animations.get(name);
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Model Name: ").append(this.name).append("\n");

        builder.append("Bones: ").append("\n");
        this.getBones().forEach(bone -> builder.append(bone.toString("  ")).append("\n"));

        return builder.toString();
    }

}
