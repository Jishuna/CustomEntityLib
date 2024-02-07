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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.joml.Vector3f;

public class BBModelParser {
    public static final Gson GSON = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Cube.class, new CubeDeserializer())
            .registerTypeAdapter(CubeFace.class, new CubeFaceDeserializer())
            .registerTypeAdapter(Animation.class, new AnimationDeserializer())
            .registerTypeAdapter(Animator.class, new AnimatorDeserializer())
            .setPrettyPrinting()
            .create();

    public static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180f);

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
        Map<UUID, Bone> bones = new LinkedHashMap<>();
        Map<String, Animation> animations = new HashMap<>();

        this.root.getAsJsonArray("elements").forEach(entry -> {
            UUID id = GSON.fromJson(entry.getAsJsonObject().get("uuid"), UUID.class);
            Cube cube = GSON.fromJson(entry, Cube.class);

            cubes.put(id, cube);
        });

        this.root.getAsJsonArray("outliner").forEach(entry -> {
            Bone bone = readBone(entry.getAsJsonObject(), cubes, new Vector3f());
            bones.put(bone.getId(), bone);
        });

        if (this.root.has("animations")) {
            this.root.getAsJsonArray("animations").forEach(entry -> {
                String name = entry.getAsJsonObject().get("name").getAsString();
                Animation animation = GSON.fromJson(entry, Animation.class);

                animations.put(name, animation);
            });
        }

        File file = new File("resource-pack");
        ResourcePackBuilder builder = new ResourcePackBuilder(file);
        bones.values().forEach(bone -> writeBone(builder, bone));

        return new EntityModel(bones, animations);
    }

    private void writeBone(ResourcePackBuilder builder, Bone bone) {
        builder.writeBone(bone);
        for (Bone child : bone.getChildren()) {
            writeBone(builder, child);
        }
    }

    private Bone readBone(JsonObject json, Map<UUID, Cube> cubes, Vector3f parentTranslation) {
        String name = json.get("name").getAsString();
        UUID id = GSON.fromJson(json.get("uuid"), UUID.class);
        Vector3f origin = new Vector3f(GSON.fromJson(json.get("origin"), float[].class));
        Vector3f absolutePosition = origin.div(16, 16, -16, new Vector3f());
        Vector3f position = absolutePosition.sub(parentTranslation, new Vector3f());

        List<Cube> boneCubes = new ArrayList<>();
        List<Bone> children = new ArrayList<>();
        json.getAsJsonArray("children").forEach(child -> {
            if (child.isJsonObject()) {
                Bone childBone = readBone(child.getAsJsonObject(), cubes, absolutePosition);
                children.add(childBone);
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

        BoneTransformation transformation = new BoneTransformation(position, new Vector3f(), new Vector3f(1));
        if (json.has("rotation")) {
            transformation.rotation.set(GSON.fromJson(json.get("rotation"), float[].class)).mul(-DEGREES_TO_RADIANS, DEGREES_TO_RADIANS, DEGREES_TO_RADIANS);
        }

        ElementScale.Result processResult = ElementScale.process(origin, boneCubes);

        Bone bone = new Bone(id, name, transformation, processResult.elements().toArray(Cube[]::new), children.toArray(Bone[]::new));
        return bone;
    }
}
