package me.jishuna.customentitylib.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import me.jishuna.customentitylib.BoneEntity;

public abstract class ModelEntity {
    private static final Color DEFAULT_DAMAGE_TINT = Color.fromRGB(255, 55, 55);

    private final EntityModel model;
    private final Map<UUID, BoneEntity> bones = new HashMap<>();

    private int damageTicks;
    private int cmd = 2;

    protected ModelEntity(Location location, EntityModel model) {
        this.model = model;

        Entity parent = location.getWorld().spawn(location, Zombie.class);
        createBoneEntities(location, parent);
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
        World world = location.getWorld();

        this.model.getBones().forEach((id, bone) -> world.spawn(location, ItemDisplay.class, e -> {
            ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setCustomModelData(this.cmd++);
            meta.setColor(Color.WHITE);
            item.setItemMeta(meta);
            e.setItemStack(item);
            e.setItemDisplayTransform(ItemDisplayTransform.HEAD);

            e.setTransformationMatrix(bone.getMatrix());
            parent.addPassenger(e);
            e.setTeleportDuration(3);

            this.bones.put(id, new BoneEntity(e));
        }));
    }
}
