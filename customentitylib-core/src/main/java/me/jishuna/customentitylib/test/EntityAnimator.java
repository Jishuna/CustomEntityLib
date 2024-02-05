package me.jishuna.customentitylib.test;

import me.jishuna.customentitylib.BoneEntity;

public class EntityAnimator {
    private final ModelEntity entity;

    private Animation animation;
    private int frame;

    public EntityAnimator(ModelEntity entity) {
        this.entity = entity;
    }

    public void tick() {
        if (this.animation == null) {
            return;
        }

        this.frame = (this.frame + 1) % this.animation.getLength();
        this.animation.tick(this.entity, this.frame);
        this.entity.getBones().values().forEach(BoneEntity::updateTransformation);
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
