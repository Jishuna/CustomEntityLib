package me.jishuna.customentitylib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.customentitylib.nms.NMS;
import me.jishuna.customentitylib.test.BBModelParser;

public class CustomEntityLib extends JavaPlugin {
    public static final Gson GSON = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(AnimationFrame.class, new AnimationFrameDeserializer())
            .create();

    private me.jishuna.customentitylib.test.EntityModel model;

    @Override
    public void onEnable() {
        NMS.initialize(this);

        File file = new File(getDataFolder(), "otter.bbmodel");

        this.model = BBModelParser.fromFile(file).parse();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        new me.jishuna.customentitylib.test.TestModelEntity(player.getLocation(), this.model);
        return true;
    }

    private void read(JsonObject json) {
        Map<UUID, Bone> bones = new HashMap<>();
        Map<UUID, Pose> defaultPoses = new HashMap<>();
        Map<String, Animation> animations = new HashMap<>();

        json.getAsJsonObject("rig").getAsJsonObject("node_map").entrySet().forEach(entry -> {
            Bone bone = GSON.fromJson(entry.getValue(), Bone.class);

            bones.put(bone.getUuid(), bone);
        });

        json.getAsJsonObject("rig").getAsJsonArray("default_pose").forEach(entry -> {
            UUID id = GSON.fromJson(entry.getAsJsonObject().get("uuid"), UUID.class);
            Pose pose = GSON.fromJson(entry, Pose.class);

            defaultPoses.put(id, pose);
        });

        json.getAsJsonObject("animations").entrySet().forEach(entry -> {
            String name = entry.getKey();
            Animation animation = GSON.fromJson(entry.getValue(), Animation.class);

            animations.put(name, animation);
        });

        // this.model = new EntityModel(bones, defaultPoses, animations);
    }
}
