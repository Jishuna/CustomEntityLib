package me.jishuna.cel.bukkit.nms.v1_20_r1.entity;

import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftMob;

import me.jishuna.cel.bukkit.entity.BukkitCustomEntity;
import me.jishuna.cel.bukkit.entity.pathfinder.PathfinderGoal;
import me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder.WrappedPathfinderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

public class CraftCustomEntity extends CraftMob implements BukkitCustomEntity {
    private final MinecraftCustomEntity handle;

    public CraftCustomEntity(CraftServer server, MinecraftCustomEntity entity) {
        super(server, entity);
        this.handle = entity;
    }

    @Override
    public MinecraftCustomEntity getHandle() {
        return handle;
    }

    @Override
    public void moveTo(double x, double y, double z, double speed) {
        this.getHandle().getNavigation().moveTo(x, y, z, speed);
    }

    @Override
    public void addPathfinderGoal(int priority, PathfinderGoal goal) {
        if (goal instanceof Goal nmsGoal) {
            this.handle.goalSelector.addGoal(priority, nmsGoal);
        } else {
            this.handle.goalSelector.addGoal(priority, new WrappedPathfinderGoal(goal));
        }
    }

    @Override
    public void spawn(double x, double y, double z) {
        this.handle.setPos(x, y, z);
        this.handle.level().addFreshEntity(this.handle);
    }
}
