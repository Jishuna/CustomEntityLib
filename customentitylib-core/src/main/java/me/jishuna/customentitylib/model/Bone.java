package me.jishuna.customentitylib.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.UUID;
import me.jishuna.customentitylib.test.BoneTransformation;

public class Bone {

    private final UUID id;
    private final String name;
    private final BoneTransformation transformation;
    private final Cube[] cubes;
    private final Bone[] children;
    private final int customModelData;

    public Bone(UUID id, String name, BoneTransformation transformation, Cube[] cubes, Bone[] children, int customModelData) {
        this.id = id;
        this.name = name;
        this.transformation = transformation;
        this.cubes = cubes;
        this.children = children;
        this.customModelData = customModelData;
    }

    public JsonObject asJsonObject(Collection<String> textureNames) {
        JsonObject root = new JsonObject();
        JsonObject textures = new JsonObject();
        int textureId = 0;
        for (String texture : textureNames) {
            textures.addProperty(Integer.toString(textureId++), "test:models/" + texture.replace(".png", ""));
        }
        root.add("textures", textures);

        JsonArray elements = new JsonArray();

        for (Cube cube : this.cubes) {
            elements.add(cube.asJsonObject());
        }

        root.add("elements", elements);
        return root;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BoneTransformation getTransformation() {
        return this.transformation;
    }

    public Cube[] getCubes() {
        return this.cubes;
    }

    public Bone[] getChildren() {
        return this.children;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }
}
