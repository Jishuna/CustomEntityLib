package me.jishuna.customentitylib;

import java.util.List;
import org.bukkit.entity.ItemDisplay;
import org.joml.Vector3f;
import me.jishuna.customentitylib.test.BoneTransformation;

public class BoneEntity {
    private final ItemDisplay entity;
    private final BoneTransformation defaultTransformation;
    private final BoneTransformation currentTransformation;
    private final List<BoneEntity> children;

    private final Vector3f parentTranslation = new Vector3f();
    private final Vector3f parentScale = new Vector3f();

    public BoneEntity(ItemDisplay entity, BoneTransformation defaultTransformation, List<BoneEntity> children) {
        this.entity = entity;
        this.defaultTransformation = defaultTransformation;
        this.currentTransformation = defaultTransformation.clone();
        this.children = children;
    }

    public void setTranslation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.currentTransformation.translation.set(new Vector3f(vector).add(this.defaultTransformation.translation));
        this.children.forEach(child -> {
            child.parentTranslation.set(vector);
        });
    }

    public void setRotation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.currentTransformation.rotation.set(vector.add(this.defaultTransformation.rotation));
    }

    public void setScale(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.currentTransformation.scale.set(new Vector3f(vector).add(this.defaultTransformation.scale));
        this.children.forEach(child -> {
            child.parentScale.set(vector);
        });
    }

    public void updateTransformation() {
        BoneTransformation transformation = this.currentTransformation.clone();
        transformation.translation.add(this.parentTranslation);
        transformation.scale.add(this.parentScale);

        this.entity.setTransformationMatrix(transformation.compose());
    }

    public ItemDisplay getEntity() {
        return this.entity;
    }
}
