package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerDoorHandler extends CollisionHandler {
    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    Entity question;

    protected void onCollisionBegin(Entity player, Entity desk) {
        question = FXGL.spawn("buttonicon", desk.getX() + 50, desk.getY() + 30);
    }

    protected void onCollisionEnd(Entity player, Entity desk) {
        question.removeFromWorld();
    }
}
