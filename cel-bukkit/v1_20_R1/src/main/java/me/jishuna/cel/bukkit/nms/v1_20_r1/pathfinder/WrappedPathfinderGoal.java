package me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder;

import me.jishuna.cel.bukkit.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

public class WrappedPathfinderGoal extends Goal {
    private final PathfinderGoal wrapped;

    public WrappedPathfinderGoal(PathfinderGoal wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean canUse() {
        return this.wrapped.canUse();
    }

    @Override
    public void start() {
        this.wrapped.start();
    }

    @Override
    public void stop() {
        this.wrapped.stop();
    }

    @Override
    public void tick() {
        this.wrapped.tick();
    }
}
