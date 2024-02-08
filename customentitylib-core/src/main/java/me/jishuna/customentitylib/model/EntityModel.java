package me.jishuna.customentitylib.model;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.joml.Vector3f;
import me.jishuna.customentitylib.animation.Animation;
import me.jishuna.customentitylib.resourcepack.ResourcePack;
import me.jishuna.customentitylib.resourcepack.Texture;
import me.jishuna.customentitylib.resourcepack.model.ItemModel;
import me.jishuna.customentitylib.resourcepack.model.ModelDisplay;
import me.jishuna.customentitylib.resourcepack.model.ModelOverride;
import me.jishuna.customentitylib.resourcepack.model.ModelPredicate;

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

    public void write(ResourcePack pack) {
        this.bones.values().forEach(bone -> writeBone(pack, bone));
    }

    private void writeBone(ResourcePack pack, Bone bone) {
        String modelName = this.name.toLowerCase() + "/" + bone.getName().toLowerCase();
        ItemModel model = pack.getItemModel("test", modelName + ".json");
        int textureId = 0;
        for (Texture texture : this.textures.values()) {
            pack.writeTexture("test", "models/" + texture.getName(), texture);
            model.textures.put(Integer.toString(textureId++), "test:models/" + texture.getName().replace(".png", ""));
        }

        Collections.addAll(model.elements, bone.getCubes());
        model.display.put("head", new ModelDisplay(new Vector3f(0, 0, 0), new Vector3f(4, 4, 4)));

        pack.getItemModel("minecraft", "item/leather_horse_armor.json").overrides
                .add(new ModelOverride("test:" + modelName, new ModelPredicate(bone.getCustomModelData())));

        for (Bone child : bone.getChildren()) {
            writeBone(pack, child);
        }
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
