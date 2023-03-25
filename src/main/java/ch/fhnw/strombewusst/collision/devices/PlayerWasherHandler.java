package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerWasherHandler extends PlayerDeskHandler {
    public PlayerWasherHandler() {
        super(EntityType.PLAYER, EntityType.WASHER);
    }

}
