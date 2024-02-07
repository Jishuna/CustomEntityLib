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
import me.jishuna.customentitylib.test.TestModelEntity;

public class CustomEntityLib extends JavaPlugin {
    private EntityModel model;

    @Override
    public void onEnable() {
        NMS.initialize(this);

        File file = new File(getDataFolder(), "creeper.bbmodel");

        this.model = BBModelParser.fromFile(file).parse();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        TestModelEntity entity = new TestModelEntity(player.getLocation(), this.model);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            entity.getAnimator().tick();
            entity.getBones().values().forEach(BoneEntity::updateTransformation);
        }, 0, 1);

        return true;
    }
}
