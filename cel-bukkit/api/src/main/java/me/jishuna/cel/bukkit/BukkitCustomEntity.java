package me.jishuna.cel.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Mob;

public interface BukkitCustomEntity extends Mob {

    public default void moveTo(Location location, double speed) {
        moveTo(location.getX(), location.getX(), location.getZ(), speed);
    }

    public void moveTo(double x, double y, double z, double speed);
    
    public void addPathfinderGoal(int priority, PathfinderGoal goal);

}
