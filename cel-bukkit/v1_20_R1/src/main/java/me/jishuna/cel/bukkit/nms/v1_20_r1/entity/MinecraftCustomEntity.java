package me.jishuna.cel.bukkit.nms.v1_20_r1.entity;

import me.jishuna.cel.bukkit.entity.CustomEntity;
import me.jishuna.cel.bukkit.entity.CustomEntityType;
import me.jishuna.cel.bukkit.nms.v1_20_r1.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class MinecraftCustomEntity extends PathfinderMob {
    private final CraftCustomEntity craftEntity;
    private final CustomEntity customEntity;
    private AttributeMap attributes;

    public MinecraftCustomEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);

        this.craftEntity = new CraftCustomEntity(world.getCraftServer(), this);
        this.customEntity = createCustomEntity();
    }

    private CustomEntity createCustomEntity() {
        CustomEntityType type = Utils.fromMinecraft(super.getType());
        if (type == null) {
            return null;
        }
        return type.createInstance(this.craftEntity);
    }

    @Override
    public AttributeMap getAttributes() {
        if (this.attributes == null) {
            this.attributes = new AttributeMap(Zombie.createAttributes().build());
        }
        return this.attributes;
    }

    @Override
    public void tick() {
        if (this.customEntity.tick()) {
            super.tick();
        }
    }

    @Override
    public CraftCustomEntity getBukkitEntity() {
        return this.craftEntity;
    }

    public CustomEntity getCustomEntity() {
        return this.customEntity;
    }
}
