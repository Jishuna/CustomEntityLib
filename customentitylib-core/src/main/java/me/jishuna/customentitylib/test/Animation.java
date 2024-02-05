package me.jishuna.customentitylib.test;

import java.util.Map;
import java.util.UUID;
import me.jishuna.customentitylib.BoneEntity;

public class Animation {
    private final Map<UUID, Animator> animators;
    private final int length;

    public Animation(int length, Map<UUID, Animator> animators) {
        this.length = length;
        this.animators = animators;
    }

    public int getLength() {
        return this.length;
    }

    public Map<UUID, Animator> getAnimators() {
        return this.animators;
    }

    public void tick(ModelEntity entity, int frame) {
        this.animators.forEach((boneId, animator) -> {
            BoneEntity bone = entity.getBones().get(boneId);
            if (bone != null) {
                animator.tick(bone, frame);
            }
        });
    }

    @Override
    public String toString() {
        return "Animation [animators=" + this.animators + "]";
    }
}
