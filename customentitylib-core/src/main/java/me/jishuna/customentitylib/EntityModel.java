package me.jishuna.customentitylib;

import java.util.Map;
import java.util.UUID;

public class EntityModel {
    private final Map<UUID, Bone> bones;
    private final Map<UUID, Pose> defaultPoses;
    private final Map<String, Animation> animations;

    public EntityModel(Map<UUID, Bone> bones, Map<UUID, Pose> defaultPoses, Map<String, Animation> animations) {
        this.bones = bones;
        this.defaultPoses = defaultPoses;
        this.animations = animations;
    }

    public Map<UUID, Bone> getBones() {
        return this.bones;
    }

    public Map<UUID, Pose> getDefaultPoses() {
        return this.defaultPoses;
    }

    public Map<String, Animation> getAnimations() {
        return this.animations;
    }
}
