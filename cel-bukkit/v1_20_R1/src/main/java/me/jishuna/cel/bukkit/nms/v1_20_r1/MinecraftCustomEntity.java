package me.jishuna.cel.bukkit.nms.v1_20_r1;

import me.jishuna.cel.bukkit.CustomEntity;
import me.jishuna.cel.bukkit.CustomEntityType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class MinecraftCustomEntity extends PathfinderMob {
    private CraftCustomEntity craftEntity;
    private CustomEntity customEntity;

    protected MinecraftCustomEntity(EntityType<? extends PathfinderMob> type, Level world) {
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
    public void tick() {
        if (this.customEntity.tick()) {
            super.tick();
        }
    }

    @Override
    public CraftCustomEntity getBukkitEntity() {
        return this.craftEntity;
    }
}
