package ch.fhnw.strombewusst.collision.Geräte;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerFridgeHandler extends PlayerDeskHandler {
    public PlayerFridgeHandler() {
        super(EntityType.PLAYER, EntityType.FRIDGE);
    }
}




