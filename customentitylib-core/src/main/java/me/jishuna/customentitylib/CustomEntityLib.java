package me.jishuna.customentitylib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import org.bukkit.Bukkit;
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

        me.jishuna.customentitylib.test.TestModelEntity entity = new me.jishuna.customentitylib.test.TestModelEntity(player.getLocation(), this.model);
        Bukkit.getScheduler().runTaskTimer(this, () -> entity.getAnimator().tick(), 0, 1);
        Bukkit.getScheduler().runTaskLater(this, () -> entity.getAnimator().setAnimation(entity.getAnimation("walk")), 20 * 5);

        return true;
    }
}
