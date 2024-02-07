package me.jishuna.customentitylib;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.customentitylib.nms.BoneEntity;
import me.jishuna.customentitylib.nms.NMS;
import me.jishuna.customentitylib.test.BBModelParser;
import me.jishuna.customentitylib.test.EntityModel;
import me.jishuna.customentitylib.test.ModelManager;
import me.jishuna.customentitylib.test.TestModelEntity;

public class CustomEntityLib extends JavaPlugin {
    private ModelManager manager = new ModelManager();

    @Override
    public void onEnable() {
        NMS.initialize(this);

        for (File file : getDataFolder().listFiles()) {
            if (file.isFile() && file.getName().endsWith(".bbmodel")) {
                EntityModel model = BBModelParser.fromFile(file).parse();
                this.manager.registerModel(model);
            }
        }

        this.manager.generateResourcePack(new File(getDataFolder(), "resource-pack"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        EntityModel model = this.manager.getModel(args[0]);
        if (model == null) {
            sender.sendMessage("Invalid model");
            return true;
        }

        TestModelEntity entity = new TestModelEntity(player.getLocation(), model);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            entity.getAnimator().tick();
            entity.getBones().values().forEach(BoneEntity::updateTransformation);
        }, 0, 1);

        return true;
    }
}
