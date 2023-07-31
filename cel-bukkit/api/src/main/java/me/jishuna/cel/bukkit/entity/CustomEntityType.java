package me.jishuna.cel.bukkit.entity;

import java.util.function.Function;

import org.bukkit.NamespacedKey;

public class CustomEntityType {
    private final NamespacedKey key;
    private final Function<BukkitCustomEntity, CustomEntity> factory;

    public CustomEntityType(NamespacedKey key, Function<BukkitCustomEntity, CustomEntity> factory) {
        this.key = key;
        this.factory = factory;
    }

    public CustomEntity createInstance(BukkitCustomEntity bukkitEntity) {
        return this.factory.apply(bukkitEntity);
    }

    public NamespacedKey getKey() {
        return this.key;
    }
}
