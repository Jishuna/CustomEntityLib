package me.jishuna.customentitylib;

import java.util.Map.Entry;
import java.util.UUID;
import org.joml.Matrix4f;

public class Animator {
    private final ModelEntity entity;

    private Animation animation;
    private int currentFrame;

    public Animator(ModelEntity entity) {
        this.entity = entity;
    }

    public void tick() {
        if (this.animation == null) {
            return;
        }

        if (++this.currentFrame >= this.animation.getFrames().length) {
            this.currentFrame = 0;
        }

        AnimationFrame frame = this.animation.getFrames()[this.currentFrame];
        for (Entry<UUID, Pose> entry : frame.getPoses().entrySet()) {
            BoneEntity boneEntity = this.entity.getBones().get(entry.getKey());
            boneEntity.getEntity().setInterpolationDuration(1);
            boneEntity.getEntity().setTransformationMatrix(new Matrix4f().set(entry.getValue().getMatrix()));
        }
    }

    public void playAnimation(Animation animation) {
        this.animation = animation;
    }
}
