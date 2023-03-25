package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerRouterHandler extends PlayerDeskHandler {

    public PlayerRouterHandler(){
        super(EntityType.PLAYER, EntityType.ROUTER);
    }
}
