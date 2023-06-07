package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

/**
 * Implements the logic of collisions between a player and a door
 */
public class PlayerDoorHandler extends CollisionHandler {
    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    private Entity question;

    @Override
    protected void onCollisionBegin(Entity player, Entity door) {
        if (door.getBoolean("open")) {
            question = FXGL.spawn("buttonicon", door.getX() + 50, door.getY() + 30);
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity door) {
        if (question != null) {
            question.removeFromWorld();
        }
    }
}
