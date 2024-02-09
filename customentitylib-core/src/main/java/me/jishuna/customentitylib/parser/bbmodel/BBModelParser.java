package me.jishuna.customentitylib.parser.bbmodel;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import me.jishuna.customentitylib.adapter.Vector4fAdapter;
import me.jishuna.customentitylib.animation.Animation;
import me.jishuna.customentitylib.animation.Animator;
import me.jishuna.customentitylib.model.Bone;
import me.jishuna.customentitylib.model.EntityModel;
import me.jishuna.customentitylib.resourcepack.Texture;
import me.jishuna.customentitylib.resourcepack.model.ModelElement;

public class BBModelParser {
    public static final Vector3f MINECRAFT_OFFSET = new Vector3f(8);

    static final Gson GSON = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .registerTypeAdapter(Vector4f.class, new Vector4fAdapter())
            .registerTypeAdapter(ModelElement.class, new ElementDeserializer())
            .registerTypeAdapter(Animation.class, new AnimationDeserializer())
            .registerTypeAdapter(Animator.class, new AnimatorDeserializer())
            .registerTypeAdapter(Texture.class, new TextureDeserializer())
            .create();

    public static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180f);

    private static final AtomicInteger MODEL_DATA_COUNTER = new AtomicInteger(2);

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
        Map<UUID, ModelElement> cubes = new LinkedHashMap<>();
        Map<UUID, Bone> bones = new LinkedHashMap<>();
        Map<String, Animation> animations = new LinkedHashMap<>();
        Map<String, Texture> textures = new LinkedHashMap<>();

        String modelName = this.root.get("name").getAsString();

        this.root.getAsJsonArray("elements").forEach(entry -> {
            UUID id = GSON.fromJson(entry.getAsJsonObject().get("uuid"), UUID.class);
            ModelElement cube = GSON.fromJson(entry, ModelElement.class);

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

        this.root.getAsJsonArray("textures").forEach(entry -> {
            Texture texture = GSON.fromJson(entry, Texture.class);

            textures.put(texture.getId(), texture);
        });

        return new EntityModel(modelName, bones, animations, textures);
    }

    private Bone readBone(JsonObject json, Map<UUID, ModelElement> cubes, Vector3f parentTranslation) {
        String name = json.get("name").getAsString();
        UUID id = GSON.fromJson(json.get("uuid"), UUID.class);
        Vector3f origin = new Vector3f(GSON.fromJson(json.get("origin"), float[].class));
        Vector3f absolutePosition = origin.div(-16, 16, -16, new Vector3f());
        Vector3f position = absolutePosition.sub(parentTranslation, new Vector3f());

        List<ModelElement> boneCubes = new ArrayList<>();
        List<Bone> children = new ArrayList<>();
        json.getAsJsonArray("children").forEach(child -> {
            if (child.isJsonObject()) {
                Bone childBone = readBone(child.getAsJsonObject(), cubes, absolutePosition);
                children.add(childBone);
            } else {
                UUID cubeId = GSON.fromJson(child, UUID.class);
                ModelElement cube = cubes.remove(cubeId);

                boneCubes.add(transformElement(origin, cube));
            }
        });

        Matrix4f matrix = new Matrix4f();
        matrix.setTranslation(position);
        if (json.has("rotation")) {
            float[] rotation = GSON.fromJson(json.get("rotation"), float[].class);
            matrix
                    .setRotationZYX(org.joml.Math.toRadians(-rotation[2]),
                            org.joml.Math.toRadians(rotation[1]),
                            org.joml.Math.toRadians(-rotation[0]));
        }

        return new Bone(id, name, matrix, boneCubes.toArray(ModelElement[]::new), children.toArray(Bone[]::new), MODEL_DATA_COUNTER.getAndIncrement());
    }

    private ModelElement transformElement(Vector3f origin, ModelElement element) {
        element.from.sub(origin).div(4).add(MINECRAFT_OFFSET);
        element.to.sub(origin).div(4).add(MINECRAFT_OFFSET);
        element.rotation.origin.sub(origin).div(4).add(MINECRAFT_OFFSET);

        return element;
    }
}
