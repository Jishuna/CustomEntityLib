package me.jishuna.customentitylib;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class Bone {
    private String type;
    private String name;
    private UUID uuid;
    @SerializedName("custom_model_data")
    private int customModelData;
    @SerializedName("resource_location")
    private String resourceLocation;

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public String getResourceLocation() {
        return this.resourceLocation;
    }

    @Override
    public String toString() {
        return "Bone [type=" + this.type + ", name=" + this.name + ", uuid=" + this.uuid + ", customModelData=" + this.customModelData + ", resourceLocation=" + this.resourceLocation + "]";
    }
}
