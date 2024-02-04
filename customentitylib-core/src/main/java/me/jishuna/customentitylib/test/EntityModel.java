package me.jishuna.customentitylib.test;

import java.util.Map;
import java.util.UUID;

public class EntityModel {
    private final Map<UUID, Bone> bones;

    public EntityModel(Map<UUID, Bone> bones) {
        this.bones = bones;
    }

    public Map<UUID, Bone> getBones() {
        return this.bones;
    }
}
