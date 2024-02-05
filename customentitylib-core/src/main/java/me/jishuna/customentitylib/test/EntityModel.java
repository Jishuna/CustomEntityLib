package me.jishuna.customentitylib.test;

import java.util.Map;
import java.util.UUID;

public class EntityModel {
    private final Map<UUID, Bone> bones;
    private final Map<String, Animation> animations;

    public EntityModel(Map<UUID, Bone> bones, Map<String, Animation> animations) {
        this.bones = bones;
        this.animations = animations;
    }

    public Map<UUID, Bone> getBones() {
        return this.bones;
    }

    public Animation getAnimation(String name) {
        return this.animations.get(name);
    }
}
