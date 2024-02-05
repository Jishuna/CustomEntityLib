package me.jishuna.customentitylib.test;

import java.util.Arrays;
import org.joml.Vector3f;
import me.jishuna.customentitylib.BoneEntity;

public class AnimationChannel {
    private final Keyframe[] keyframes;

    public AnimationChannel() {
        this(new Keyframe[0]);
    }

    public AnimationChannel(Keyframe[] keyframes) {
        this.keyframes = keyframes;
    }

    public Vector3f tick(BoneEntity bone, int frame) {
        Keyframe previous = null;

        for (Keyframe keyFrame : this.keyframes) {
            if (keyFrame.getTime() == frame) {
                return new Vector3f(keyFrame.getValue());
            }

            if (keyFrame.getTime() > frame) {
                return lerpFrames(previous, keyFrame, frame);
            }

            previous = keyFrame;
        }
        return null;
    }

    private Vector3f lerpFrames(Keyframe previous, Keyframe next, int frame) {
        if (previous == null) {
            return null;
        }

        float progress = (frame - previous.getTime()) / (float) (next.getTime() - previous.getTime());
        return previous.getValue().lerp(next.getValue(), progress, new Vector3f());
    }

    public Keyframe[] getKeyframes() {
        return this.keyframes;
    }

    @Override
    public String toString() {
        return "AnimationChannel [keyframes=" + Arrays.toString(this.keyframes) + "]";
    }
}
