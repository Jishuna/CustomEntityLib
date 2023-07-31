package me.jishuna.cel.bukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.Mob;

import me.jishuna.cel.bukkit.entity.pathfinder.PathfinderGoal;

public interface BukkitCustomEntity extends Mob {

    public default void moveTo(Location location, double speed) {
        moveTo(location.getX(), location.getX(), location.getZ(), speed);
    }

    public void moveTo(double x, double y, double z, double speed);

    public void addPathfinderGoal(int priority, PathfinderGoal goal);

    public default void spawn(Location location) {
        spawn(location.getX(), location.getY(), location.getZ());
    }

    public void spawn(double x, double y, double z);

}
