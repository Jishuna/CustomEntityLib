package me.jishuna.customentitylib.nms.latest;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.entity.ModelEntity;
import me.jishuna.customentitylib.nms.NMSAdapter;

public class NMSAdapterImpl implements NMSAdapter {

    @Override
    public Entity spawnCustomEntity(Location location, ModelEntity modelEntity) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();

        CustomEntity entity = new CustomEntity(level, modelEntity);
        entity.setPos(location.getX(), location.getY(), location.getZ());
        HtiboxEntity hitbox = entity.getHitboxEntity();
        hitbox.setPos(location.getX(), location.getY(), location.getZ());

        level.addFreshEntity(entity);
        level.addFreshEntity(hitbox);
        hitbox.startRiding(entity);

        return entity.getBukkitEntity();
    }
}
