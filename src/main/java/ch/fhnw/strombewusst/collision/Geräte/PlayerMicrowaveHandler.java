package ch.fhnw.strombewusst.collision.Geräte;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerMicrowaveHandler extends PlayerDeskHandler {
        public PlayerMicrowaveHandler() {
            super(EntityType.PLAYER, EntityType.MICROWAVE);
        }
}

