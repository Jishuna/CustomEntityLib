package me.jishuna.customentitylib.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.entity.ItemDisplay;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import me.jishuna.customentitylib.model.Bone;

public class BoneEntity {
    private final Bone bone;
    private final Matrix4f animationMatrix = new Matrix4f();
    private final ItemDisplay display;
    private final List<BoneEntity> children = new ArrayList<>();
    private final AtomicBoolean dirty = new AtomicBoolean();

    public BoneEntity(Bone bone, ItemDisplay display) {
        this.bone = bone;
        this.display = display;
    }

    public void addChild(BoneEntity child) {
        this.children.add(child);
    }

    public void updateTransform(Matrix4f parent) {
        Matrix4f transform = parent.mul(this.bone.getTransform().mul(this.animationMatrix, new Matrix4f()), new Matrix4f());

        if (this.dirty.get()) {
            this.display.setInterpolationDelay(0);
            this.display.setInterpolationDuration(1);
            this.display.setTransformationMatrix(transform);
            this.dirty.compareAndSet(true, false);
        }

        this.children.forEach(child -> child.updateTransform(transform));
    }

    public void setTranslation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.animationMatrix.setTranslation(vector);
        setDirty();
    }

    public void setRotation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.animationMatrix
                .setRotationZYX(Math.toRadians(-vector.z),
                        Math.toRadians(-vector.y),
                        Math.toRadians(vector.x));
        setDirty();
    }

    public void setDirty() {
        this.dirty.compareAndSet(false, true);
        this.children.forEach(BoneEntity::setDirty);
    }

    public ItemDisplay getDisplay() {
        return this.display;
    }
}
