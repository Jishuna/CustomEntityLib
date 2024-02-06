package me.jishuna.customentitylib.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.UUID;

public class Bone {

    private final UUID id;
    private final String name;
    private final BoneTransformation transformation;
    private final Cube[] cubes;
    private final Bone[] children;

    public Bone(UUID id, String name, BoneTransformation transformation, Cube[] cubes, Bone[] children) {
        this.id = id;
        this.name = name;
        this.transformation = transformation;
        this.cubes = cubes;
        this.children = children;
    }

    public JsonObject asJsonObject() {
        JsonObject root = new JsonObject();
        JsonObject textures = new JsonObject();
        textures.addProperty("0", "test:entity/nocsy_otter_v2");
        root.add("textures", textures);

        JsonArray elements = new JsonArray();

        for (Cube cube : this.cubes) {
            elements.add(cube.asJsonObject());
        }

        root.add("elements", elements);
        JsonObject display = new JsonObject();
        JsonObject head = new JsonObject();
        JsonArray rotation = new JsonArray();
        rotation.add(0);
        rotation.add(180);
        rotation.add(0);
        head.add("rotation", rotation);
        display.add("head", head);
        root.add("display", display);

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
}
