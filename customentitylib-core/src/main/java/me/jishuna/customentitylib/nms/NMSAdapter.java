package me.jishuna.customentitylib.nms;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import me.jishuna.customentitylib.ModelEntity;

public interface NMSAdapter {
    public Entity spawnCustomEntity(Location location, ModelEntity modelEntity);
}
