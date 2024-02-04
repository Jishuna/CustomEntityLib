package me.jishuna.customentitylib;

import java.util.Arrays;

public class Pose {
    private float[] matrix;

    public float[] getMatrix() {
        return this.matrix;
    }

    @Override
    public String toString() {
        return "Pose [matrix=" + Arrays.toString(this.matrix) + "]";
    }
}
