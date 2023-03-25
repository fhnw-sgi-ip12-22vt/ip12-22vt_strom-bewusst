package ch.fhnw.strombewusst.collision.Geräte;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerMainDeskHandler;

public class PlayerPS5Handler extends PlayerDeskHandler {
    public PlayerPS5Handler(){
        super(EntityType.PLAYER, EntityType.PS5);
    }
}
