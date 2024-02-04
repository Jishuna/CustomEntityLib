package me.jishuna.customentitylib.nms.latest;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.ModelEntity;
import me.jishuna.customentitylib.nms.NMSAdapter;

public class NMSAdapterImpl implements NMSAdapter {
    private static SynchedEntityData data;

    @Override
    public Entity spawnCustomEntity(Location location, ModelEntity modelEntity) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();

        if (data == null) {
            ArmorStand stand = EntityType.ARMOR_STAND.create(level);
            stand.setInvisible(true);
            stand.setMarker(true);

            data = stand.getEntityData();
        }

        CustomEntity entity = new CustomEntity(level, data, modelEntity);
        entity.setPos(location.getX(), location.getY(), location.getZ());
        HtiboxEntity hitbox = entity.getHitboxEntity();
        hitbox.setPos(location.getX(), location.getY(), location.getZ());

        level.addFreshEntity(entity);
        level.addFreshEntity(hitbox);
        hitbox.startRiding(entity);

        return hitbox.getBukkitEntity();
    }
}
