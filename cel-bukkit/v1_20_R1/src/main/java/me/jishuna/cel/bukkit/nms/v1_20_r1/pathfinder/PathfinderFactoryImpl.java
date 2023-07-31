package me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder;

import me.jishuna.cel.bukkit.entity.CustomEntity;
import me.jishuna.cel.bukkit.entity.pathfinder.PathfinderFactory;
import me.jishuna.cel.bukkit.entity.pathfinder.PathfinderGoal;
import me.jishuna.cel.bukkit.nms.v1_20_r1.Utils;
import net.minecraft.world.entity.player.Player;

public class PathfinderFactoryImpl extends PathfinderFactory {

    @Override
    public PathfinderGoal createNearestTargetGoal(CustomEntity custom, boolean checkVisibility) {
        return new CustomNearestAttackableTargetGoal<>(Utils.toMinecraft(custom), Player.class, checkVisibility);
    }

    @Override
    public PathfinderGoal createMeleeAttackGoal(CustomEntity custom, double speed, boolean pauseWhenIdle) {
        return new CustomMeleeAttackGoal(Utils.toMinecraft(custom), speed, pauseWhenIdle);
    }
}
