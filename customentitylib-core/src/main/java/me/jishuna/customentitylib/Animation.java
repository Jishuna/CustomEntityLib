package me.jishuna.customentitylib;

import java.util.Arrays;

public class Animation {
    private AnimationFrame[] frames;

    public AnimationFrame[] getFrames() {
        return this.frames;
    }

    @Override
    public String toString() {
        return "Animation [frames=" + Arrays.toString(this.frames) + "]";
    }
}
