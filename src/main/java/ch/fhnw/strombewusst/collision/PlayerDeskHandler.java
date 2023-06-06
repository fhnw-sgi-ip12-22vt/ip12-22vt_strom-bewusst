package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import java.util.List;

/**
 * Implements the logic of collisions between players and desks.
 */
public class PlayerDeskHandler extends CollisionHandler {
    private final List<String> infoBoxes = FXGL.getAssetLoader().loadText(Config.ROOM_1_TEXT_PATH);

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity desk) {
        int deskNum = desk.getComponent(DeskComponent.class).getDeskNum();

        if (player.getInt("playerNum") == 1) {
            FXGL.set("player1InfoText", infoBoxes.get(deskNum));
        } else {
            FXGL.set("player2InfoText", infoBoxes.get(deskNum));
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        try {
            String varName;
            if (player.getInt("playerNum") == 1) {
                varName = "player1InfoText";
            } else {
                varName = "player2InfoText";
            }

            int deskNum = desk.getComponent(DeskComponent.class).getDeskNum();

            if (FXGL.gets(varName).equals(infoBoxes.get(deskNum))) {
                FXGL.set(varName, "");
            }
        } catch (IllegalArgumentException ignored) {
            // BUGFIX: prevent crash on edge-case when text is shown during level change
        }
    }
}
