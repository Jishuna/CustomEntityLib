package me.jishuna.cel.bukkit.nms.v1_20_r1;

import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;

import me.jishuna.cel.bukkit.CustomEntity;
import me.jishuna.cel.bukkit.CustomEntityLib;
import me.jishuna.cel.bukkit.CustomEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class Utils {

    public static CustomEntityType fromMinecraft(EntityType<?> type) {
        ResourceLocation nmsKey = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        NamespacedKey key = CraftNamespacedKey.fromMinecraft(nmsKey);

        return CustomEntityLib.getEntityTypeRegistry().get(key);
    }

    public static MinecraftCustomEntity toMinecraft(CustomEntity entity) {
        CraftCustomEntity craftEntity = (CraftCustomEntity) entity.asBukkitEntity();
        return craftEntity.getHandle();
    }
}
