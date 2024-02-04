package me.jishuna.customentitylib;

import org.bukkit.entity.ItemDisplay;

public class BoneEntity {
    private final ItemDisplay entity;

    public BoneEntity(ItemDisplay entity) {
        this.entity = entity;
    }

    public ItemDisplay getEntity() {
        return this.entity;
    }
}
