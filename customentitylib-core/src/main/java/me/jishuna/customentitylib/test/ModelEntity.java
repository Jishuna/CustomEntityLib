package me.jishuna.customentitylib.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import me.jishuna.customentitylib.nms.BoneEntity;
import me.jishuna.customentitylib.nms.NMS;

public abstract class ModelEntity {
    private static final Color DEFAULT_DAMAGE_TINT = Color.fromRGB(255, 100, 100);

    private final EntityAnimator animator;
    private final EntityModel model;
    private final Map<UUID, BoneEntity> bones = new HashMap<>();

    private int damageTicks;
    private int cmd = 2;

    protected ModelEntity(Location location, EntityModel model) {
        this.model = model;
        this.animator = new EntityAnimator(this);

        Entity parent = NMS.getAdapter().spawnCustomEntity(location, this);
        createBoneEntities(location, parent);
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

        BoneEntity entity = NMS.getAdapter().spawnBoneEntity(parent, location, bone.getTransformation(), parentBone, bone.getName().startsWith("h_"));
        ItemDisplay display = entity.getDisplay();

        ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setCustomModelData(this.cmd++);
        meta.setColor(Color.WHITE);
        item.setItemMeta(meta);
        display.setItemStack(item);
        display.setItemDisplayTransform(ItemDisplayTransform.HEAD);

        display.setTransformationMatrix(bone.getTransformation().compose());
        display.setTeleportDuration(3);

        parent.addPassenger(display);
        this.bones.put(bone.getId(), entity);

        for (Bone childBone : bone.getChildren()) {
            createBoneEntitiesRecursive(location, childBone, parent, entity);
        }
    }
}
