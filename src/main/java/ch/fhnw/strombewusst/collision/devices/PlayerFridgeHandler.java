package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerFridgeHandler extends PlayerDeskHandler {
    public PlayerFridgeHandler() {
        super(EntityType.PLAYER, EntityType.FRIDGE);
    }
}




