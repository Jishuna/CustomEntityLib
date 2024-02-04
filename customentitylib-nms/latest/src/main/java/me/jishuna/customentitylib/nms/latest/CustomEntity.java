package me.jishuna.customentitylib.nms.latest;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import me.jishuna.customentitylib.ModelEntity;

public class CustomEntity extends Silverfish {
    private final HtiboxEntity hitbox;
    private final ModelEntity modelEntity;
    private final SynchedEntityData data;

    public CustomEntity(Level world, SynchedEntityData data, ModelEntity modelEntity) {
        super(EntityType.SILVERFISH, world);
        this.hitbox = new HtiboxEntity(this);
        this.modelEntity = modelEntity;
        this.data = data;

        setSilent(true);
        refreshDimensions();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.modelEntity.onDamage();

        boolean hurt = super.hurt(source, amount);
        if (getHealth() <= 0) {
            this.modelEntity.playAnimation("death");
        }

        return hurt;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(getId(), getUUID(), getX(), getY(), getZ(), getXRot(), getYRot(), EntityType.ARMOR_STAND, 0, getDeltaMovement(), getYHeadRot());
    }

    @Override
    public SynchedEntityData getEntityData() {
        return this.data == null ? super.getEntityData() : this.data;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return new EntityDimensions(this.hitbox.getWidth(), this.hitbox.getHeight(), super.getDimensions(pose).fixed);
    }

    @Override
    public void tick() {
        super.tick();
        this.modelEntity.tick();

        this.hitbox.getPassengers().forEach(e -> e.setYRot(getYRot()));
    }

    @Override
    public void remove(RemovalReason reason) {
        this.hitbox.remove(reason);
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
}
