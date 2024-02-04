package me.jishuna.customentitylib;

import java.util.Map;
import java.util.UUID;

public class AnimationFrame {
    private final Map<UUID, Pose> poses;

    public AnimationFrame(Map<UUID, Pose> poses) {
        this.poses = poses;
    }

    public Map<UUID, Pose> getPoses() {
        return this.poses;
    }

    @Override
    public String toString() {
        return "AnimationFrame [poses=" + this.poses + "]";
    }
}
