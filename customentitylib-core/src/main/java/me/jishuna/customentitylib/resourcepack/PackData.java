package me.jishuna.customentitylib.resourcepack;

import com.google.gson.annotations.SerializedName;

public class PackData {
    @SerializedName("pack_format")
    public int packFormat;
    public String description;

    public PackData() {
        this.packFormat = 0;
        this.description = "";
    }
}
