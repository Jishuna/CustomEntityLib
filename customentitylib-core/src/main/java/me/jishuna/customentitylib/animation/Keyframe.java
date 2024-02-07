package me.jishuna.customentitylib.animation;

import org.joml.Vector3f;

public class Keyframe {
    private final int time;
    private final Vector3f value;

    public Keyframe(int time, Vector3f value) {
        this.time = time;
        this.value = value;
    }

    public int getTime() {
        return this.time;
    }

    public Vector3f getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Keyframe [time=" + this.time + ", value=" + this.value + "]";
    }
}
