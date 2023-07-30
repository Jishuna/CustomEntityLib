package me.jishuna.cel.bukkit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

public class EntityTypeRegistry {
    private final Map<NamespacedKey, CustomEntityType> registryMap = new HashMap<>();

    public void register(NamespacedKey key, CustomEntityType type) {
        this.registryMap.put(key, type);
    }

    public CustomEntityType get(NamespacedKey key) {
        return this.registryMap.get(key);
    }
}
