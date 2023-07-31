package me.jishuna.cel.bukkit.entity;

public class CustomEntity {
    private final BukkitCustomEntity bukkitEntity;

    public CustomEntity(BukkitCustomEntity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
    }

    public boolean tick() {
        return true;
    }

    public BukkitCustomEntity asBukkitEntity() {
        return this.bukkitEntity;
    }

}
