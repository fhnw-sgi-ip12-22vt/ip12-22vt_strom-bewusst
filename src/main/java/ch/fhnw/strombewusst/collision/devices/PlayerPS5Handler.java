package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerPS5Handler extends PlayerDeskHandler {
    public PlayerPS5Handler(){
        super(EntityType.PLAYER, EntityType.PS5);
    }
}
