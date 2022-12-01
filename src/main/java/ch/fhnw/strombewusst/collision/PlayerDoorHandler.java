package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.StromBewusst;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerDoorHandler extends CollisionHandler {
    private Entity desk;

    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity door) {
        player.getComponent(PlayerComponent.class).setIsNearDoor(true);
    }
    protected void onCollisionEnd(Entity player, Entity door) {

    }
}
