package me.jishuna.customentitylib.model;

import java.util.Map;
import java.util.UUID;
import me.jishuna.customentitylib.animation.Animation;
import me.jishuna.customentitylib.resourcepack.Texture;

public class EntityModel {
    private final String name;
    private final Map<UUID, Bone> bones;
    private final Map<String, Animation> animations;
    private final Map<String, Texture> textures;

    public EntityModel(String name, Map<UUID, Bone> bones, Map<String, Animation> animations, Map<String, Texture> textures) {
        this.name = name;
        this.bones = bones;
        this.animations = animations;
        this.textures = textures;
    }

    public String getName() {
        return this.name;
    }

    public Map<UUID, Bone> getBones() {
        return this.bones;
    }

    public Animation getAnimation(String name) {
        return this.animations.get(name);
    }

    public Map<String, Texture> getTextures() {
        return this.textures;
    }
}
