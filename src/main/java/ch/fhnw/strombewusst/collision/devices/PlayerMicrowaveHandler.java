package ch.fhnw.strombewusst.collision.devices;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;

public class PlayerMicrowaveHandler extends PlayerDeskHandler {
        public PlayerMicrowaveHandler() {
            super(EntityType.PLAYER, EntityType.MICROWAVE);
        }
}

