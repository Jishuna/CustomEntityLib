package me.jishuna.cel.bukkit.nms.v1_20_r1;

import org.bukkit.craftbukkit.v1_20_R1.util.CraftNamespacedKey;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.jishuna.cel.bukkit.entity.CustomEntityType;
import me.jishuna.cel.bukkit.nms.NMSAdapter;
import me.jishuna.cel.bukkit.nms.v1_20_r1.entity.MinecraftCustomEntity;
import me.jishuna.cel.bukkit.nms.v1_20_r1.packet.EntityRemapListener;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class NMSAdapterImpl extends NMSAdapter {

    @Override
    public void onLoad(Plugin plugin) {
        Utils.unfreezeRegistry(BuiltInRegistries.ENTITY_TYPE);
    }

    @Override
    public void onEnable(Plugin plugin) {
        BuiltInRegistries.ENTITY_TYPE.freeze();
        
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new EntityRemapListener(plugin));
    }

    @Override
    public void registerInternalEntityType(CustomEntityType type) {
        ResourceLocation key = CraftNamespacedKey.toMinecraft(type.getKey());
        EntityType<Entity> nmsType = EntityType.Builder.of(MinecraftCustomEntity::new, MobCategory.MONSTER).build(key.getPath());

        Registry.register(BuiltInRegistries.ENTITY_TYPE, key, nmsType);
    }
}
