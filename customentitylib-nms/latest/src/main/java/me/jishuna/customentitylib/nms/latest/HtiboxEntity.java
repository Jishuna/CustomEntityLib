package me.jishuna.customentitylib.nms.latest;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Interaction;

public class HtiboxEntity extends Interaction {
    private final CustomEntity delegtate;

    public HtiboxEntity(CustomEntity delegate) {
        super(EntityType.INTERACTION, delegate.level());
        this.delegtate = delegate;

        setWidth(1);
        setHeight(1);

        this.noPhysics = true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return this.delegtate.hurt(source, amount);
    }

    @Override
    public boolean skipAttackInteraction(Entity attacker) {
        return false;
    }

    @Override
    public void remove(RemovalReason reason) {
        getPassengers().forEach(e -> e.remove(reason));
        super.remove(reason);
    }
}
