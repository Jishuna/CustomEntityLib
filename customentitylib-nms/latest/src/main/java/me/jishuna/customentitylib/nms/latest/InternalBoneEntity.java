package me.jishuna.customentitylib.nms.latest;

import com.mojang.math.Transformation;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import me.jishuna.customentitylib.BoneTransformation;
import me.jishuna.customentitylib.nms.BoneEntity;
import me.jishuna.customentitylib.parser.bbmodel.BBModelParser;

public class InternalBoneEntity extends ItemDisplay implements BoneEntity {
    private final Entity parent;
    private final BoneTransformation defaultTransformation;
    private final BoneTransformation amimTransformation;
    private final BoneEntity parentBone;
    private final boolean headBone;

    public InternalBoneEntity(Entity parent, BoneTransformation defaultTransformation, BoneEntity parentBone, boolean headBone) {
        super(EntityType.ITEM_DISPLAY, parent.level());
        this.parent = parent;
        this.defaultTransformation = defaultTransformation;
        this.amimTransformation = new BoneTransformation();
        this.parentBone = parentBone;
        this.headBone = headBone;
    }

    @Override
    public void setTranslation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.amimTransformation.translation.set(new Vector3f(vector));
    }

    @Override
    public void setRotation(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.amimTransformation.rotation.set(new Vector3f(vector));
    }

    @Override
    public void setScale(Vector3f vector) {
        if (vector == null) {
            return;
        }

        this.amimTransformation.scale.set(new Vector3f(vector));
    }

    @Override
    public void updateTransformation() {
        Matrix4f matrix = getFinalMatrix(this.headBone);

        setTransformationInterpolationDelay(0);
        setTransformationInterpolationDuration(3);
        setTransformation(new Transformation(matrix));
    }

    @Override
    public org.bukkit.entity.ItemDisplay getDisplay() {
        return (org.bukkit.entity.ItemDisplay) getBukkitEntity();
    }

    @Override
    public Matrix4f getDefaultMatrix() {
        return this.defaultTransformation.compose();
    }

    @Override
    public Matrix4f getAnimationMatrix() {
        return this.amimTransformation.compose();
    }

    @Override
    public Matrix4f getFinalMatrix(boolean head) {
        Matrix4f matrix = new Matrix4f();
        if (this.parentBone != null) {
            matrix.mul(this.parentBone.getFinalMatrix(head));
        } else {
            matrix.rotateY((head ? this.parent.getYHeadRot() : this.parent.getYRot()) * -BBModelParser.DEGREES_TO_RADIANS);
        }

        matrix.mul(this.defaultTransformation.compose());
        matrix.mul(this.amimTransformation.compose());
        return matrix;
    }
}
