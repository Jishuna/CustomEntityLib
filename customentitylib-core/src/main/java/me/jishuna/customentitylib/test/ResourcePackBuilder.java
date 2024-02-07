package me.jishuna.customentitylib.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ResourcePackBuilder {
    private final File root;
    private final File minecraftFolder;
    private final File modelsFolder;
    private final File texturesFolder;

    private final String namespace;
    private final List<ModelOverride> overrides = new ArrayList<>();

    public ResourcePackBuilder(File root, String namespace) {
        this.root = root;
        this.namespace = namespace;

        File assetsFolder = makeFolder(root, "assets");
        this.minecraftFolder = makeFolder(assetsFolder, "minecraft");
        File namespaceRoot = makeFolder(assetsFolder, namespace);
        this.modelsFolder = makeFolder(namespaceRoot, "models");
        File textureFolder = makeFolder(namespaceRoot, "textures");
        this.texturesFolder = makeFolder(textureFolder, "models");
    }

    public void writeModels(Collection<EntityModel> models) {
        models.forEach(this::writeModel);

        JsonObject itemModel = new JsonObject();
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", "minecraft:item/leather_horse_armor");
        itemModel.add("textures", textures);
        itemModel.addProperty("parent", "item/generated");

        this.overrides.sort(Comparator.comparing(ModelOverride::customModelData));

        JsonArray overridesArray = new JsonArray();
        this.overrides.forEach(overide -> overridesArray.add(overide.asJsonObject()));
        itemModel.add("overrides", overridesArray);

        File minecraftModelFolder = makeFolder(this.minecraftFolder, "models");
        File itemFolder = makeFolder(minecraftModelFolder, "item");
        File modelFile = new File(itemFolder, "leather_horse_armor.json");

        try (Writer writer = Files.newBufferedWriter(modelFile.toPath(), StandardCharsets.UTF_8)) {
            BBModelParser.GSON.toJson(itemModel, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeModel(EntityModel model) {
        File modelFolder = makeFolder(this.modelsFolder, model.getName());
        model.getBones().values().forEach(bone -> writeBones(modelFolder, bone, model));

        model.getTextures().forEach((id, texture) -> {
            File target = new File(this.texturesFolder, texture.getName());

            try {
                Files.write(target.toPath(), texture.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeBones(File folder, Bone bone, EntityModel model) {
        writeBone(folder, bone, model);

        for (Bone child : bone.getChildren()) {
            writeBones(folder, child, model);
        }
    }

    private void writeBone(File folder, Bone bone, EntityModel model) {
        File file = new File(folder, bone.getName().toLowerCase() + ".json");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            BBModelParser.GSON.toJson(bone.asJsonObject(model.getTextures().values().stream().map(Texture::getName).toList()), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(this.namespace);
        builder.append(":");
        builder.append(folder.getName());
        builder.append("/");
        builder.append(bone.getName().toLowerCase());

        this.overrides.add(new ModelOverride(builder.toString(), bone.getCustomModelData()));
    }

    private File makeFolder(File parent, String name) {
        File folder = new File(parent, name);
        folder.mkdirs();

        return folder;
    }

}
