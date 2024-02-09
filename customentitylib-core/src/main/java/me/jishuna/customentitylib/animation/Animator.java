package me.jishuna.customentitylib.animation;

import me.jishuna.customentitylib.entity.BoneEntity;

public class Animator {
    private final AnimationChannel position;
    private final AnimationChannel rotation;
    private final AnimationChannel scale;

    public Animator() {
        this(new AnimationChannel(), new AnimationChannel(), new AnimationChannel());
    }

    public Animator(AnimationChannel position, AnimationChannel rotation, AnimationChannel scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void tick(BoneEntity bone, int frame) {
        bone.setTranslation(this.position.tick(bone, frame));
        bone.setRotation(this.rotation.tick(bone, frame));
    }

    public AnimationChannel getPosition() {
        return this.position;
    }

    public AnimationChannel getRotation() {
        return this.rotation;
    }

    public AnimationChannel getScale() {
        return this.scale;
    }

    @Override
    public String toString() {
        return "Animator [position=" + this.position + ", rotation=" + this.rotation + ", scale=" + this.scale + "]";
    }

}
