package me.jishuna.cel.bukkit.entity;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

import me.jishuna.cel.bukkit.nms.NMSAdapter;

public class EntityTypeRegistry {
    private final Map<NamespacedKey, CustomEntityType> registryMap = new HashMap<>();

    public void register(CustomEntityType type) {
        this.registryMap.put(type.getKey(), type);
        NMSAdapter.getInstance().registerInternalEntityType(type);
    }

    public CustomEntityType get(NamespacedKey key) {
        return this.registryMap.get(key);
    }
}
