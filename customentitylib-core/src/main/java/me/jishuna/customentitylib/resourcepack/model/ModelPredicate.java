package me.jishuna.customentitylib.resourcepack.model;

import com.google.gson.annotations.SerializedName;
import me.jishuna.customentitylib.Utils;

public class ModelPredicate {
    @SerializedName("custom_model_data")
    public Integer customModelData;

    public ModelPredicate() {
        this(null);
    }

    public ModelPredicate(Integer customModelData) {
        this.customModelData = Utils.getOr(customModelData, () -> 0);
    }

}
