package me.jishuna.customentitylib.resourcepack.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hash(this.customModelData);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModelPredicate other)) {
            return false;
        }
        return Objects.equals(this.customModelData, other.customModelData);
    }

}
