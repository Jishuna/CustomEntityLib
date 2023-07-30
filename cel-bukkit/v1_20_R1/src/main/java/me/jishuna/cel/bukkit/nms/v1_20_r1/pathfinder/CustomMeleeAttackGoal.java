package me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder;

import me.jishuna.cel.bukkit.PathfinderGoal;
import me.jishuna.cel.bukkit.nms.v1_20_r1.MinecraftCustomEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CustomMeleeAttackGoal extends MeleeAttackGoal implements PathfinderGoal {

    public CustomMeleeAttackGoal(MinecraftCustomEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }
}
