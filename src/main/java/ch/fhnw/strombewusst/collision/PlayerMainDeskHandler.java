package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerMainDeskHandler extends CollisionHandler {
    public PlayerMainDeskHandler() {
        super(EntityType.PLAYER, EntityType.MAINDESK);
    }

    Entity question;

    protected void onCollisionBegin(Entity player, Entity desk) {
        question = entityBuilder()
                .at(desk.getX() + 10, desk.getY() - 40)
                .view("Q.png")
                .buildAndAttach();
        player.getComponent(PlayerComponent.class).setIsNearDesk(true);
    }

    protected void onCollisionEnd(Entity player, Entity desk) {
        player.getComponent(PlayerComponent.class).setIsNearDesk(false);
        question.removeFromWorld();
    }
}
