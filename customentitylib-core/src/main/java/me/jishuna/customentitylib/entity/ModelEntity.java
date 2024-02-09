package me.jishuna.customentitylib.entity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.joml.Math;
import org.joml.Matrix4f;
import me.jishuna.customentitylib.animation.Animation;
import me.jishuna.customentitylib.animation.Priority;
import me.jishuna.customentitylib.model.Bone;
import me.jishuna.customentitylib.model.EntityModel;
import me.jishuna.customentitylib.nms.NMS;

public abstract class ModelEntity {
    private static final Color DEFAULT_DAMAGE_TINT = Color.fromRGB(255, 100, 100);

    private final EntityAnimator animator;
    private final EntityModel model;
    private final Entity parentEntity;
    private final Set<BoneEntity> parentBones = new HashSet<>();
    private final Map<UUID, BoneEntity> bones = new LinkedHashMap<>();

    private int damageTicks;

    protected ModelEntity(Location location, EntityModel model) {
        this.model = model;
        this.animator = new EntityAnimator(this);

        this.parentEntity = NMS.getAdapter().spawnCustomEntity(location, this);
        createBoneEntities(location, this.parentEntity);
    }

    public EntityAnimator getAnimator() {
        return this.animator;
    }

    public Animation getAnimation(String name) {
        return this.model.getAnimation(name);
    }

    public void tick() {
        if ((this.damageTicks > 0) && (--this.damageTicks <= 0)) {
            setColor(Color.WHITE, -1);
        }
    }

    public void asyncTick() {
        this.animator.tick();
        Matrix4f matrix = new Matrix4f();
        matrix.setRotationXYZ(0, -Math.toRadians(this.parentEntity.getLocation().getYaw()), 0);

        this.parentBones.forEach(bone -> bone.updateTransform(matrix));
    }

    public void onDeath() {
        setColor(DEFAULT_DAMAGE_TINT, Integer.MAX_VALUE);

        this.animator.clearAll();
        this.animator.queueAnimation(getAnimation("death"), Priority.HIGHEST);
    }

    public void onDamage(float damage) {
        setColor(DEFAULT_DAMAGE_TINT, 10);
    }

    public void setColor(Color color, int duration) {
        this.damageTicks = duration;

        this.bones.values().forEach(boneEntity -> {
            ItemDisplay entity = boneEntity.getDisplay();
            ItemStack item = entity.getItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
            entity.setItemStack(item);
        });
    }

    public Map<UUID, BoneEntity> getBones() {
        return this.bones;
    }

    private void createBoneEntities(Location location, Entity parent) {

        this.model.getBones().values().forEach(bone -> createBoneEntitiesRecursive(location, bone, parent, null));
    }

    private void createBoneEntitiesRecursive(Location location, Bone bone, Entity parent, BoneEntity parentBone) {
        if (bone == null) {
            return;
        }

        ItemDisplay display = location.getWorld().spawn(location, ItemDisplay.class, e -> {
            ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setCustomModelData(bone.getCustomModelData());
            meta.setColor(Color.WHITE);
            item.setItemMeta(meta);
            e.setItemStack(item);
            e.setItemDisplayTransform(ItemDisplayTransform.HEAD);

            e.setTeleportDuration(3);
        });

        parent.addPassenger(display);

        BoneEntity boneEntity = new BoneEntity(bone, display);
        this.bones.put(bone.getId(), boneEntity);

        if (parentBone != null) {
            parentBone.addChild(boneEntity);
        } else {
            this.parentBones.add(boneEntity);
        }

        for (Bone childBone : bone.getChildren()) {
            createBoneEntitiesRecursive(location, childBone, parent, boneEntity);
        }
    }
}
