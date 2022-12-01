package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerDoorHandler extends CollisionHandler {
    private Entity desk;

    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        desk = FXGL.spawn("main-desk", 0, 0);
    }

    protected void onCollisionEnd(Entity a, Entity b) {
        desk.removeFromWorld();
    }
}
