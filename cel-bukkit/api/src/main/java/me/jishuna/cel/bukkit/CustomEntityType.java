package me.jishuna.cel.bukkit;

import java.util.function.Function;

public class CustomEntityType {

    private final Function<BukkitCustomEntity, CustomEntity> factory;

    public CustomEntityType(Function<BukkitCustomEntity, CustomEntity>  factory) {
        this.factory = factory;
    }

    public CustomEntity createInstance(BukkitCustomEntity bukkitEntity) {
        return this.factory.apply(bukkitEntity);
    }
}
