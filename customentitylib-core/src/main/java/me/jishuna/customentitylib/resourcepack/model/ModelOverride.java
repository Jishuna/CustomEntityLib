package me.jishuna.customentitylib.resourcepack.model;

import java.util.Objects;
import me.jishuna.customentitylib.Utils;

public class ModelOverride implements Comparable<ModelOverride> {
    public String model;
    public final ModelPredicate predicate;

    public ModelOverride() {
        this(null, null);
    }

    public ModelOverride(String model, ModelPredicate predicate) {
        this.model = model;
        this.predicate = Utils.getOr(predicate, ModelPredicate::new);
    }

    @Override
    public int compareTo(ModelOverride other) {
        return this.predicate.customModelData.compareTo(other.predicate.customModelData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.model, this.predicate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModelOverride other)) {
            return false;
        }
        return Objects.equals(this.predicate, other.predicate);
    }
}
