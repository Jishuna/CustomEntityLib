package me.jishuna.customentitylib.nms.latest;

import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftCreature;

public class ModelCraftEntity extends CraftCreature {

    public ModelCraftEntity(CraftServer server, PathfinderMob entity) {
        super(server, entity);
    }
}
