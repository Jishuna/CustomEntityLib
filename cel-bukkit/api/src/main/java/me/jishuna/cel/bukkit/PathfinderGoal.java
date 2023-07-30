package me.jishuna.cel.bukkit;

public interface PathfinderGoal {

    public boolean canUse();

    public void start();

    public void stop();

    public void tick();
}
