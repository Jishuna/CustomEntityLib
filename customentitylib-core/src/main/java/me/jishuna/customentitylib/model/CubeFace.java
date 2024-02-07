package me.jishuna.customentitylib.model;

import com.google.gson.JsonObject;
import org.joml.Vector4f;

public class CubeFace {
    private final Vector4f uv;
    private final int textureIndex;
    private final int tintIndex;

    public CubeFace(Vector4f uv, int textureIndex) {
        this(uv, textureIndex, 0);
    }

    public CubeFace(Vector4f uv, int textureIndex, int tintIndex) {
        uv.div(64);
        this.uv = uv;
        this.textureIndex = textureIndex;
        this.tintIndex = tintIndex;
    }

    public JsonObject asJsonObject() {
        JsonObject root = new JsonObject();
        root.add("uv", BBModelParser.GSON.toJsonTree(new float[] { this.uv.x * 16f, this.uv.y * 16f, this.uv.z * 16f, this.uv.w * 16f }));
        root.addProperty("texture", "#" + this.textureIndex);
        root.addProperty("tintindex", this.tintIndex);

        return root;
    }

    @Override
    public String toString() {
        return "CubeFace [uv=" + this.uv + ", textureIndex=" + this.textureIndex + ", tintIndex=" + this.tintIndex + "]";
    }
}
