package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeviceComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import java.util.List;

/**
 * Implements the logic of collisions between players and devices.
 */
public class PlayerDeviceHandler extends CollisionHandler {

    private final List<String> infoBoxes = FXGL.getAssetLoader().loadText(Config.ROOM_2_TEXT_PATH);
    public PlayerDeviceHandler() {
        super(EntityType.PLAYER, EntityType.DEVICE);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity device) {
        int deviceNum = device.getComponent(DeviceComponent.class).getDeviceNum();

        if (player.getInt("playerNum") == 1) {
            FXGL.set("player1InfoText", infoBoxes.get(deviceNum));
        } else {
            FXGL.set("player2InfoText", infoBoxes.get(deviceNum));
        }
    }

    protected void onCollisionEnd(Entity player, Entity device) {
        try {
            String varName;
            if (player.getInt("playerNum") == 1) {
                varName = "player1InfoText";
            } else {
                varName = "player2InfoText";
            }

            int deviceNum = device.getComponent(DeviceComponent.class).getDeviceNum();

            if (FXGL.gets(varName).equals(infoBoxes.get(deviceNum))) {
                FXGL.set(varName, "");
            }
        } catch (IllegalArgumentException ignored) {
            // BUGFIX: prevent crash on edge-case when text is shown during level change
        }
    }
}
