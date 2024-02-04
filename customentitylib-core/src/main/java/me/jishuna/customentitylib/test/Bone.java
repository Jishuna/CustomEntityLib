package me.jishuna.customentitylib.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Arrays;
import org.joml.Matrix4f;

public class Bone {

    private final String name;
    private final Matrix4f matrix;
    private final Cube[] cubes;

    public Bone(String name, Matrix4f matrix, Cube[] cubes) {
        this.name = name;
        this.matrix = matrix;
        this.cubes = cubes;
    }

    public String getName() {
        return this.name;
    }

    public Matrix4f getMatrix() {
        return this.matrix;
    }

    public Cube[] getCubes() {
        return this.cubes;
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
        return root;
    }

    @Override
    public String toString() {
        return "Bone [name=" + this.name + ", matrix=" + this.matrix + ", cubes=" + Arrays.toString(this.cubes) + "]";
    }
}
