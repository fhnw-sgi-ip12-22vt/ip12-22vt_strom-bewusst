package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerStoveHandler extends PlayerDeskHandler {
    public PlayerStoveHandler(){
        super(EntityType.PLAYER, EntityType.STOVE);
    }
}
