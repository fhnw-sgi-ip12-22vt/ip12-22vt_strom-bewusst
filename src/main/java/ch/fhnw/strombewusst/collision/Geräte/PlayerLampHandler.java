package ch.fhnw.strombewusst.collision.Geräte;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerLampHandler extends PlayerDeskHandler {
    public PlayerLampHandler(){
        super(EntityType.PLAYER, EntityType.LAMP);
    }
}
