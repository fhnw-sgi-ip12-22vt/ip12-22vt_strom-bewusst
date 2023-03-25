package ch.fhnw.strombewusst.collision.Geräte;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerRouterHandler extends PlayerDeskHandler {

    public PlayerRouterHandler(){
        super(EntityType.PLAYER, EntityType.ROUTER);
    }
}
