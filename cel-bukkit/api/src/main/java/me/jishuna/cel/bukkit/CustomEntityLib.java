package me.jishuna.cel.bukkit;

import me.jishuna.cel.ModelRegistry;

public class CustomEntityLib {
    private static final CustomEntityLib instance = new CustomEntityLib();

    private final ModelRegistry modelRegistry = new ModelRegistry();
    private final EntityTypeRegistry entityTypeRegistry = new EntityTypeRegistry();

    public static ModelRegistry getModelRegistry() {
        return getInstance().modelRegistry;
    }

    public static EntityTypeRegistry getEntityTypeRegistry() {
        return getInstance().entityTypeRegistry;
    }

    private static CustomEntityLib getInstance() {
        return instance;
    }
}
