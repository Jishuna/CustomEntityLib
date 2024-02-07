package me.jishuna.customentitylib.nms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.BoneTransformation;
import me.jishuna.customentitylib.entity.ModelEntity;

public interface NMSAdapter {
    public Entity spawnCustomEntity(Location location, ModelEntity modelEntity);

    public BoneEntity spawnBoneEntity(Entity parent, Location location, BoneTransformation defaultTransformation, BoneEntity parentBone, boolean headBone);
}
