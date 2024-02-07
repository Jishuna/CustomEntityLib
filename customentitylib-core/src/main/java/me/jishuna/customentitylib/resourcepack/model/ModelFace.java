package me.jishuna.customentitylib.resourcepack.model;

import com.google.gson.annotations.SerializedName;
import org.joml.Vector4f;
import me.jishuna.customentitylib.Utils;

public class ModelFace {
    public String texture;
    @SerializedName("tintindex")
    public int tintIndex;
    public final Vector4f uv;

    public ModelFace() {
        this(null, null, null);
    }

    public ModelFace(String texture, Integer tintIndex, Vector4f uv) {
        this.texture = Utils.getOr(texture, () -> "#0");
        this.tintIndex = Utils.getOr(tintIndex, () -> 0);
        this.uv = Utils.getOr(uv, Vector4f::new);
    }
}
