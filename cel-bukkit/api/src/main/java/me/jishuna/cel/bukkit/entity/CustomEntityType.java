package me.jishuna.cel.bukkit.entity;

import java.util.function.Function;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

public class CustomEntityType {
    private final NamespacedKey key;
    private final EntityType replacement;
    private final Function<BukkitCustomEntity, CustomEntity> factory;

    public CustomEntityType(NamespacedKey key, EntityType replacement ,Function<BukkitCustomEntity, CustomEntity> factory) {
        this.key = key;
        this.replacement = replacement;
        this.factory = factory;
    }

    public CustomEntity createInstance(BukkitCustomEntity bukkitEntity) {
        return this.factory.apply(bukkitEntity);
    }

    public NamespacedKey getKey() {
        return this.key;
    }

    public EntityType getReplacement() {
        return replacement;
    }
}
