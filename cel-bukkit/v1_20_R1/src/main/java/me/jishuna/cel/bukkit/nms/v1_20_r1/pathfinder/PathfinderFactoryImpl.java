package me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder;

import me.jishuna.cel.bukkit.CustomEntity;
import me.jishuna.cel.bukkit.PathfinderGoal;
import me.jishuna.cel.bukkit.nms.v1_20_r1.Utils;
import net.minecraft.world.entity.player.Player;

public class PathfinderFactoryImpl {

    public PathfinderGoal createNearestTargetGoal(CustomEntity custom, boolean checkVisibility) {
        return new CustomNearestAttackableTargetGoal<>(Utils.toMinecraft(custom), Player.class, checkVisibility);
    }

    public PathfinderGoal createMeleeAttackGoal(CustomEntity custom, double speed, boolean pauseWhenIdle) {
        return new CustomMeleeAttackGoal(Utils.toMinecraft(custom), speed, pauseWhenIdle);
    }
}
