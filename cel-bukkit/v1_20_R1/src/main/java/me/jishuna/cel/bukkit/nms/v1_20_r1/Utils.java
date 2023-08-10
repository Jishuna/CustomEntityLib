package me.jishuna.cel.bukkit.nms.v1_20_r1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;

import me.jishuna.cel.bukkit.CustomEntityLib;
import me.jishuna.cel.bukkit.entity.CustomEntity;
import me.jishuna.cel.bukkit.entity.CustomEntityType;
import me.jishuna.cel.bukkit.nms.v1_20_r1.entity.CraftCustomEntity;
import me.jishuna.cel.bukkit.nms.v1_20_r1.entity.MinecraftCustomEntity;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class Utils {

    public static CustomEntityType fromMinecraft(EntityType<?> type) {
        ResourceLocation nmsKey = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        NamespacedKey key = CraftNamespacedKey.fromMinecraft(nmsKey);

        return CustomEntityLib.getEntityTypeRegistry().get(key);
    }

    public static int getTypeId(org.bukkit.entity.EntityType type) {
        if (type == org.bukkit.entity.EntityType.UNKNOWN) {
            return -1;
        }
        NamespacedKey key = type.getKey();
        EntityType<?> nmsType = BuiltInRegistries.ENTITY_TYPE.get(CraftNamespacedKey.toMinecraft(key));
        return BuiltInRegistries.ENTITY_TYPE.getId(nmsType);
    }

    public static MinecraftCustomEntity toMinecraft(CustomEntity entity) {
        CraftCustomEntity craftEntity = (CraftCustomEntity) entity.asBukkitEntity();
        return craftEntity.getHandle();
    }

    public static Field getField(Class<?> clazz, Class<?> type, int index) {
        int i = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == type) {
                if (index == i) {
                    field.setAccessible(true);
                    return field;
                }
                i++;
            }
        }
        return null;
    }

    public static void unfreezeRegistry(Registry<?> registry) {
        try {
            Field intrusiveHolderCache = getField(MappedRegistry.class, Map.class, 5);
            intrusiveHolderCache.set(registry, new HashMap<>());

            Field frozen = getField(MappedRegistry.class, boolean.class, 0);
            frozen.set(registry, false);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
