package me.jishuna.customentitylib.nms;

import org.bukkit.entity.ItemDisplay;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface BoneEntity {
    public void setTranslation(Vector3f vector);

    public void setRotation(Vector3f vector);

    public void setScale(Vector3f vector);

    public void updateTransformation();

    public ItemDisplay getDisplay();

    public Matrix4f getAnimationMatrix();

    public Matrix4f getDefaultMatrix();

    public Matrix4f getFinalMatrix(boolean head);

    public boolean isDirty();

    public void setDirty(boolean dirty);

    public void addChild(BoneEntity internalBoneEntity);
}
