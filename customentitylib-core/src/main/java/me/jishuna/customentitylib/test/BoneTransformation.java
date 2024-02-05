package me.jishuna.customentitylib.test;

import org.bukkit.util.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BoneTransformation implements Cloneable {
    public final Vector3f translation;
    public final Vector3f rotation;
    public final Vector3f scale;

    public BoneTransformation() {
        this(new Vector3f(), new Vector3f(), new Vector3f(1));
    }

    public BoneTransformation(Vector3f translation, Vector3f rotation, Vector3f scale) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public BoneTransformation(Transformation transformation) {
        this(transformation.getTranslation(), transformation.getLeftRotation().getEulerAnglesXYZ(new Vector3f()), transformation.getScale());
    }

    public BoneTransformation add(BoneTransformation other) {
        return new BoneTransformation(this.translation.add(other.translation, new Vector3f()),
                this.rotation.add(other.rotation, new Vector3f()),
                new Vector3f(this.scale));
    }

    public Matrix4f compose() {
        return new Matrix4f()
                .translate(this.translation)
                .rotateXYZ(this.rotation)
                .scale(this.scale);
    }

    @Override
    public BoneTransformation clone() {
        return new BoneTransformation(new Vector3f(this.translation), new Vector3f(this.rotation), new Vector3f(this.scale));
    }

}
