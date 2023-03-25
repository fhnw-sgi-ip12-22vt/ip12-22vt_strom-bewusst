package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerTelevisionHandler extends PlayerDeskHandler {
    public PlayerTelevisionHandler(){
        super(EntityType.PLAYER, EntityType.TELEVISION);
    }
}
