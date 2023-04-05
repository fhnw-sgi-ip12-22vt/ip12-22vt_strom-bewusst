package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import java.util.List;

public class PlayerDeskHandler extends CollisionHandler {
    private final List<String> infoBoxes = FXGL.getAssetLoader().loadText("room1_deskinfo.txt");

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity type) {
        int deskNum = type.getComponent(DeskComponent.class).getDeskNum();

        if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
            FXGL.set("player1InfoText", infoBoxes.get(deskNum));
        } else {
            FXGL.set("player2InfoText", infoBoxes.get(deskNum));
        }
    }

    protected void onCollisionEnd(Entity player, Entity desk) {
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
