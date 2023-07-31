package me.jishuna.cel.bukkit.entity;

import org.bukkit.Bukkit;
import org.bukkit.World;

public abstract class EntityFactory {
    private static final String CLASS_NAME = "me.jishuna.cel.bukkit.nms.%version%.entity.EntityFactoryImpl";
    private static EntityFactory instance;

    public static EntityFactory getInstance() {
        if (instance == null) {
            try {
                instance = (EntityFactory) Class.forName(CLASS_NAME.replace("%version%", getServerVersion())).getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toLowerCase();
    }

    public abstract CustomEntity createCustomEntity(World world, CustomEntityType type);
}
