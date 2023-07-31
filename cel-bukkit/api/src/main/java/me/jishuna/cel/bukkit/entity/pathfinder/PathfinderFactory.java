package me.jishuna.cel.bukkit.entity.pathfinder;

import org.bukkit.Bukkit;

import me.jishuna.cel.bukkit.entity.CustomEntity;

public abstract class PathfinderFactory {
    private static final String CLASS_NAME = "me.jishuna.cel.bukkit.nms.%version%.pathfinder.PathfinderFactoryImpl";
    private static PathfinderFactory instance;

    public static PathfinderFactory getInstance() {
        if (instance == null) {
            try {
                instance = (PathfinderFactory) Class.forName(CLASS_NAME.replace("%version%", getServerVersion())).getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toLowerCase();
    }

    public abstract PathfinderGoal createNearestTargetGoal(CustomEntity custom, boolean checkVisibility);

    public abstract PathfinderGoal createMeleeAttackGoal(CustomEntity custom, double speed, boolean pauseWhenIdle);
}
