package me.jishuna.customentitylib.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.joml.Vector3f;

public class BBModelParser {
    public static final Gson GSON = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Cube.class, new CubeDeserializer())
            .registerTypeAdapter(CubeFace.class, new CubeFaceDeserializer())
            .setPrettyPrinting()
            .create();

    private static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180f);

    private final JsonObject root;

    private BBModelParser(JsonObject root) {
        this.root = root;
    }

    public static BBModelParser fromFile(File file) {
        try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            return new BBModelParser(JsonParser.parseReader(reader).getAsJsonObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EntityModel parse() {
        Map<UUID, Cube> cubes = new HashMap<>();
        this.root.getAsJsonArray("elements").forEach(entry -> {
            UUID id = GSON.fromJson(entry.getAsJsonObject().get("uuid"), UUID.class);
            Cube cube = GSON.fromJson(entry, Cube.class);

            cubes.put(id, cube);
        });

        Map<UUID, Bone> bones = new HashMap<>();
        this.root.getAsJsonArray("outliner").forEach(entry -> {
            readBone(entry.getAsJsonObject(), bones, cubes, new Vector3f());
        });

        File file = new File("resource-pack");
        ResourcePackBuilder builder = new ResourcePackBuilder(file);
        bones.values().forEach(builder::writeBone);

        return new EntityModel(bones);
    }

    private void readBone(JsonObject json, Map<UUID, Bone> bones, Map<UUID, Cube> cubes, Vector3f parentTranslation) {
        String name = json.get("name").getAsString();
        UUID id = GSON.fromJson(json.get("uuid"), UUID.class);
        Vector3f origin = new Vector3f(GSON.fromJson(json.get("origin"), float[].class));
        Vector3f absolutePosition = origin.div(-16, 16, -16, new Vector3f());
        Vector3f position = absolutePosition.sub(parentTranslation.x, 0, 0, new Vector3f());

        List<Cube> boneCubes = new ArrayList<>();
        json.getAsJsonArray("children").forEach(child -> {
            if (child.isJsonObject()) {
                readBone(child.getAsJsonObject(), bones, cubes, absolutePosition);
            } else {
                UUID cubeId = GSON.fromJson(child, UUID.class);
                Cube cube = cubes.remove(cubeId);
                if (cube == null) {
                    System.out.println("Null!");
                } else {
                    boneCubes.add(cube);
                }
            }
        });

        ElementScale.Result processResult = ElementScale.process(origin, boneCubes);

        BoneTransformation transformation = new BoneTransformation(position, new Vector3f(), new Vector3f(1));
        if (json.has("rotation")) {
            transformation.rotation.set(GSON.fromJson(json.get("rotation"), float[].class)).mul(-DEGREES_TO_RADIANS);
        }
        Bone bone = new Bone(name, transformation.compose(), processResult.elements().toArray(Cube[]::new));
        bones.put(id, bone);
    }
}
