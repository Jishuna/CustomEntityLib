package me.jishuna.customentitylib.resourcepack;

import com.google.gson.annotations.SerializedName;
import me.jishuna.customentitylib.Utils;

public class PackMeta {
    @SerializedName("pack")
    public final PackData packData;

    public PackMeta() {
        this(null);
    }

    public PackMeta(PackData packData) {
        this.packData = Utils.getOr(packData, PackData::new);
    }
}
