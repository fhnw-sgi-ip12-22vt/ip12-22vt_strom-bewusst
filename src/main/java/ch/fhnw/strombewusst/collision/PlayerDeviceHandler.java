package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeviceComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import java.util.List;

public class PlayerDeviceHandler extends CollisionHandler {

    private final List<String> infoBoxes = FXGL.getAssetLoader().loadText(Config.ROOM_2_TEXT_PATH);
    public PlayerDeviceHandler() {
        super(EntityType.PLAYER, EntityType.DEVICE);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity type) {
        int deviceNum = type.getComponent(DeviceComponent.class).getDeviceNum();

        if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
            FXGL.set("player1InfoText", infoBoxes.get(deviceNum));
        } else {
            FXGL.set("player2InfoText", infoBoxes.get(deviceNum));
        }
    }

    protected void onCollisionEnd(Entity player, Entity device) {
        try {
            if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
                FXGL.set("player1InfoText", "");
            } else {
                FXGL.set("player2InfoText", "");
            }
        } catch (IllegalArgumentException ignored) {
            // BUGFIX: prevent crash on edge-case when text is shown during level change
        }
    }
}
