package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerPlayerHandler extends CollisionHandler {
    public PlayerPlayerHandler() {
        super(EntityType.PLAYER, EntityType.PLAYER);
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        // resetting velocity so pushed players don't keep moving after collision
        a.getComponent(PhysicsComponent.class).setVelocityX(0);
        a.getComponent(PhysicsComponent.class).setVelocityY(0);
        b.getComponent(PhysicsComponent.class).setVelocityX(0);
        b.getComponent(PhysicsComponent.class).setVelocityY(0);
    }
}