package me.jishuna.customentitylib.entity;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.jishuna.customentitylib.animation.Animation;
import me.jishuna.customentitylib.animation.AnimationEntry;
import me.jishuna.customentitylib.animation.LoopMode;
import me.jishuna.customentitylib.animation.Priority;

public class EntityAnimator {
    private final ModelEntity entity;
    private final Queue<AnimationEntry> animationQueue = new ConcurrentLinkedQueue<>();

    private volatile AnimationEntry currentAnimation;
    private int frame;

    public EntityAnimator(ModelEntity entity) {
        this.entity = entity;
    }

    public void tick() {
        if (!hasActiveAnimation()) {
            return;
        }

        Animation active = this.currentAnimation.animation();

        this.frame++;
        if (this.frame > active.getLength()) {
            if (!this.animationQueue.isEmpty() && this.animationQueue.peek().priority().getValue() >= this.currentAnimation.priority().getValue()) {
                clearActiveAnimation();
            } else if (active.getLoopMode() == LoopMode.LOOP) {
                queueAnimation(this.currentAnimation);
            } else {
                clearActiveAnimation();
            }
        }

        if (!hasActiveAnimation()) {
            return;
        }

        active.tick(this.entity, this.frame);
    }

    public void queueAnimation(Animation animation, Priority priority) {
        queueAnimation(new AnimationEntry(animation, priority));
    }

    public void queueAnimation(AnimationEntry entry) {
        if (entry.animation() == null) {
            return;
        }

        if (!hasActiveAnimation()) {
            this.currentAnimation = entry;
        } else {
            this.animationQueue.add(entry);
        }
    }

    public void clearQueue() {
        this.animationQueue.clear();
    }

    public void clearActiveAnimation() {
        if (!this.animationQueue.isEmpty()) {
            this.currentAnimation = this.animationQueue.poll();
        } else {
            this.currentAnimation = null;
        }
        this.frame = 0;
    }

    public void clearAll() {
        clearQueue();
        clearActiveAnimation();
    }

    public boolean hasActiveAnimation() {
        return this.currentAnimation != null;
    }
}
