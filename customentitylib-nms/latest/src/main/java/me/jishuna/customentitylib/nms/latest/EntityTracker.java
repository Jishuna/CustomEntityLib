package me.jishuna.customentitylib.nms.latest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.decoration.ArmorStand;

public class EntityTracker {
    private static final byte MARKER_FLAG = 0b00010000;

    private final Set<ServerPlayerConnection> tracking = new HashSet<>();
    private final CustomEntity entity;

    public EntityTracker(CustomEntity entity) {
        this.entity = entity;
    }

    public void updateTrackers(Set<ServerPlayerConnection> trackers) {
        removeOld(trackers);
        addNew(trackers);
    }

    public void onTrackingStart(ServerPlayerConnection connection) {
        SynchedEntityData.DataItem<Byte> marker = new SynchedEntityData.DataItem<>(ArmorStand.DATA_CLIENT_FLAGS, MARKER_FLAG);
        ClientboundSetEntityDataPacket packet = new ClientboundSetEntityDataPacket(this.entity.getId(), List.of(marker.value()));
        connection.send(packet);
    }

    public void onTrackingEnd(ServerPlayerConnection connection) {
        // Nothing
    }

    private void removeOld(Set<ServerPlayerConnection> trackers) {
        Iterator<ServerPlayerConnection> iterator = this.tracking.iterator();

        while (iterator.hasNext()) {
            ServerPlayerConnection connection = iterator.next();
            if (!trackers.contains(connection)) {
                iterator.remove();
                onTrackingEnd(connection);
            }
        }
    }

    private void addNew(Set<ServerPlayerConnection> trackers) {
        trackers.forEach(connection -> {
            if (!this.tracking.contains(connection)) {
                this.tracking.add(connection);
                onTrackingStart(connection);
            }
        });
    }
}
