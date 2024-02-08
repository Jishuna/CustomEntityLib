package me.jishuna.customentitylib.nms.latest;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ChunkMap.TrackedEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.joml.Vector3f;
import me.jishuna.customentitylib.animation.Priority;
import me.jishuna.customentitylib.entity.ModelEntity;

public class CustomEntity extends PathfinderMob {
    private final HtiboxEntity hitbox;
    private final ModelEntity modelEntity;
    private final EntityTracker tracker;
    private final CraftEntity craftEntity;

    private boolean wasWalking;

    public CustomEntity(Level world, ModelEntity modelEntity) {
        super(EntityType.ZOMBIE, world);
        this.craftEntity = new ModelCraftEntity(world.getCraftServer(), this);
        this.hitbox = new HtiboxEntity(this);
        this.modelEntity = modelEntity;
        this.tracker = new EntityTracker(this);

        addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, -1, 0, true, false));
        setSilent(true);
    }

    @Override
    public CraftEntity getBukkitEntity() {
        return this.craftEntity;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(getId(), getUUID(), getX(), getY(), getZ(), getXRot(), getYRot(), EntityType.ARMOR_STAND, 0, getDeltaMovement(), getYHeadRot());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.modelEntity.onDamage(amount);

        boolean hurt = super.hurt(source, amount);
        if (getHealth() <= 0) {
            this.modelEntity.onDeath();
        }

        return hurt;
    }

    @Override
    public void tick() {
        super.tick();
        this.modelEntity.tick();
        boolean walking = updateWalking();

        if (getYRot() != this.yRotO) {
            getPassengers().forEach(e -> {
                if (e instanceof InternalBoneEntity bone) {
                    bone.setDirty(true);
                }
            });
        }

        ServerLevel level = ((ServerLevel) level());
        @SuppressWarnings("resource")
        TrackedEntity tracked = level.getChunkSource().chunkMap.entityMap.get(getId());

        if (tracked == null) {
            return;
        }

        this.tracker.updateTrackers(tracked.seenBy);
        this.wasWalking = walking;
    }

    @Override
    public void remove(RemovalReason reason) {
        getPassengers().forEach(e -> e.remove(reason));
        super.remove(reason);
    }

    public HtiboxEntity getHitboxEntity() {
        return this.hitbox;
    }

    @Override
    public float getMyRidingOffset(Entity vehicle) {
        return ridingOffset(vehicle);
    }

    @Override
    protected float ridingOffset(Entity vehicle) {
        return 0.0F;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return position();
    }

    @Override
    protected Vector3f getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0, 0, 0);
    }

    @Override
    public void setNoAi(boolean aiDisabled) {
    }

    @Override
    public void setLeftHanded(boolean leftHanded) {
    }

    @Override
    public void setAggressive(boolean attacking) {
    }

    private boolean updateWalking() {
        if (isDeadOrDying()) {
            return this.wasWalking;
        }

        boolean walking = getDeltaMovement().x != 0 || getDeltaMovement().z != 0;
        if (walking && !this.wasWalking) {
            this.modelEntity.getAnimator().queueAnimation(this.modelEntity.getAnimation("walk"), Priority.LOWEST);
        } else if (!walking && this.wasWalking) {
            this.modelEntity.getAnimator().queueAnimation(this.modelEntity.getAnimation("idle"), Priority.LOWEST);
        }

        return walking;
    }
}
