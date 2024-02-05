package me.jishuna.customentitylib.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import me.jishuna.customentitylib.BoneEntity;

public abstract class ModelEntity {
    private static final Color DEFAULT_DAMAGE_TINT = Color.fromRGB(255, 55, 55);

    private final EntityAnimator animator;
    private final EntityModel model;
    private final Map<UUID, BoneEntity> bones = new HashMap<>();

    private int damageTicks;
    private int cmd = 2;

    protected ModelEntity(Location location, EntityModel model) {
        this.model = model;
        this.animator = new EntityAnimator(this);

        Entity parent = location.getWorld().spawn(location, ArmorStand.class, e -> {
            e.setVisible(false);
            e.setMarker(true);
            e.setGravity(true);
        });
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
            setColor(Color.WHITE);
        }

        // this.animator.tick();
    }

    public void onDamage() {
        setColor(DEFAULT_DAMAGE_TINT);
        this.damageTicks = 10;
    }

    public void playAnimation(String name) {
        // this.animator.playAnimation(this.model.getAnimations().get(name));
    }

    public void setColor(Color color) {
        this.bones.values().forEach(boneEntity -> {
            ItemDisplay entity = boneEntity.getEntity();
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

        this.model.getBones().values().forEach(bone -> createBoneEntitiesRecursive(location, bone, parent));
    }

    private BoneEntity createBoneEntitiesRecursive(Location location, Bone bone, Entity parent) {
        if (bone == null) {
            return null;
        }

        List<BoneEntity> children = new ArrayList<>();
        for (Bone childBone : bone.getChildren()) {
            BoneEntity childEntity = createBoneEntitiesRecursive(location, childBone, parent);

            if (childEntity != null) {
                children.add(childEntity);
            }
        }

        World world = location.getWorld();
        ItemDisplay display = world.spawn(location, ItemDisplay.class, e -> {
            ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setCustomModelData(this.cmd++);
            meta.setColor(Color.WHITE);
            item.setItemMeta(meta);
            e.setItemStack(item);
            e.setItemDisplayTransform(ItemDisplayTransform.HEAD);

            e.setTransformationMatrix(bone.getTransformation().compose());
            e.setTeleportDuration(3);
            e.setInterpolationDuration(1);
        });

        parent.addPassenger(display);

        BoneEntity entity = new BoneEntity(display, bone.getTransformation(), children);
        this.bones.put(bone.getId(), entity);

        return entity;
    }
}
