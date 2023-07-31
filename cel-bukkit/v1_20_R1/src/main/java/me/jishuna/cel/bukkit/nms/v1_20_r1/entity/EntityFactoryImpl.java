package me.jishuna.cel.bukkit.nms.v1_20_r1.entity;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;

import me.jishuna.cel.bukkit.entity.CustomEntity;
import me.jishuna.cel.bukkit.entity.CustomEntityType;
import me.jishuna.cel.bukkit.entity.EntityFactory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityFactoryImpl extends EntityFactory {

    @Override
    public CustomEntity createCustomEntity(World world, CustomEntityType type) {
        ServerLevel nmsLevel = ((CraftWorld) world).getHandle();
        EntityType<?> nmsType = BuiltInRegistries.ENTITY_TYPE.get(CraftNamespacedKey.toMinecraft(type.getKey()));
        Entity entity = nmsType.create(nmsLevel);

        if (entity instanceof MinecraftCustomEntity custom) {
            return custom.getCustomEntity();
        }
        return null;
    }
}
