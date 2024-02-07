package me.jishuna.customentitylib.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import me.jishuna.customentitylib.resourcepack.ResourcePackBuilder;

public class ModelManager {
    private final Map<String, EntityModel> models = new HashMap<>();

    public void registerModel(EntityModel model) {
        this.models.put(model.getName(), model);
    }

    public EntityModel getModel(String name) {
        return this.models.get(name);
    }

    public void generateResourcePack(File targetFolder) {
        ResourcePackBuilder builder = new ResourcePackBuilder(targetFolder, "test");
        builder.writeModels(this.models.values());
    }

}
