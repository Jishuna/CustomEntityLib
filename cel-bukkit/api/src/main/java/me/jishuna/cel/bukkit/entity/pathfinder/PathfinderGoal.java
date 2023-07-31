package me.jishuna.cel.bukkit.entity.pathfinder;

public interface PathfinderGoal {

    public boolean canUse();

    public void start();

    public void stop();

    public void tick();
}
