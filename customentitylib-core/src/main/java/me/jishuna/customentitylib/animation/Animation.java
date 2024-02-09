package me.jishuna.customentitylib.animation;

import java.util.Map;
import java.util.UUID;
import me.jishuna.customentitylib.entity.BoneEntity;
import me.jishuna.customentitylib.entity.ModelEntity;

public class Animation {
    private final Map<UUID, Animator> animators;
    private final int length;
    private final LoopMode loopMode;

    public Animation(int length, Map<UUID, Animator> animators, LoopMode loopMode) {
        this.length = length;
        this.animators = animators;
        this.loopMode = loopMode;
    }

    public int getLength() {
        return this.length;
    }

    public Map<UUID, Animator> getAnimators() {
        return this.animators;
    }

    public LoopMode getLoopMode() {
        return this.loopMode;
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
