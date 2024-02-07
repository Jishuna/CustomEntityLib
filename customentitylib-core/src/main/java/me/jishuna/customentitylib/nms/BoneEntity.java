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

    Matrix4f getAnimationMatrix();

    Matrix4f getDefaultMatrix();

    Matrix4f getFinalMatrix(boolean head);
}
