package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerSmartPhoneHandler extends PlayerDeskHandler {
    public PlayerSmartPhoneHandler(){
        super(EntityType.PLAYER, EntityType.SMARTPHONE);
    }
}
