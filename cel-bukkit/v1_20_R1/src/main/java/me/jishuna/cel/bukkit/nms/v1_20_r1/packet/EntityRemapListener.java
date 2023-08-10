package me.jishuna.cel.bukkit.nms.v1_20_r1.packet;

import java.util.List;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.jishuna.cel.bukkit.entity.CustomEntityType;
import me.jishuna.cel.bukkit.nms.v1_20_r1.Utils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;

public class EntityRemapListener extends PacketAdapter {
    public EntityRemapListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, List.of(PacketType.Play.Server.SPAWN_ENTITY), ListenerOptions.ASYNC);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        ClientboundAddEntityPacket nmsPacket = (ClientboundAddEntityPacket) packet.getHandle();
        CustomEntityType type = Utils.fromMinecraft(nmsPacket.getType());
        if (type == null) {
            return;
        }

        packet.getEntityTypeModifier().writeSafely(0, type.getReplacement());
    }
}
