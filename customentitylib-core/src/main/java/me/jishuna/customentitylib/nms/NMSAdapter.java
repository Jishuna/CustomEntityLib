package me.jishuna.customentitylib.nms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.test.BoneTransformation;
import me.jishuna.customentitylib.test.ModelEntity;

public interface NMSAdapter {
    public Entity spawnCustomEntity(Location location, ModelEntity modelEntity);

    public BoneEntity spawnBoneEntity(Entity parent, Location location, BoneTransformation defaultTransformation, BoneEntity parentBone, boolean headBone);
}
