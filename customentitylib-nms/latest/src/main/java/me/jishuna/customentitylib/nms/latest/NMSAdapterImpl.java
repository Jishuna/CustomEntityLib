package me.jishuna.customentitylib.nms.latest;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.entity.ModelEntity;
import me.jishuna.customentitylib.nms.BoneEntity;
import me.jishuna.customentitylib.nms.NMSAdapter;
import me.jishuna.customentitylib.test.BoneTransformation;

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

    @Override
    public BoneEntity spawnBoneEntity(Entity parent, Location location, BoneTransformation defaultTransformation, BoneEntity parentBone, boolean headBone) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        net.minecraft.world.entity.Entity internal = ((CraftEntity) parent).getHandle();

        InternalBoneEntity boneEntity = new InternalBoneEntity(internal, defaultTransformation, parentBone, headBone);
        boneEntity.setPos(location.getX(), location.getY(), location.getZ());
        level.addFreshEntity(boneEntity);

        return boneEntity;
    }
}
