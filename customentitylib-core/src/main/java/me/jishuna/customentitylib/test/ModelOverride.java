package me.jishuna.customentitylib.test;

import com.google.gson.JsonObject;

public record ModelOverride(String model, int customModelData) {

    public JsonObject asJsonObject() {
        JsonObject root = new JsonObject();
        root.addProperty("model", this.model);

        JsonObject predicate = new JsonObject();
        predicate.addProperty("custom_model_data", this.customModelData);
        root.add("predicate", predicate);

        return root;
    }
}
