package me.jishuna.cel.bukkit.nms.v1_20_r1.pathfinder;

import me.jishuna.cel.bukkit.PathfinderGoal;
import me.jishuna.cel.bukkit.nms.v1_20_r1.MinecraftCustomEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class CustomNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> implements PathfinderGoal {

    public CustomNearestAttackableTargetGoal(MinecraftCustomEntity mob, Class<T> targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }
}
