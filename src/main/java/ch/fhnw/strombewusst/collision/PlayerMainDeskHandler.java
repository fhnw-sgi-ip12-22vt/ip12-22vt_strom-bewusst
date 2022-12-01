package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerMainDeskHandler extends CollisionHandler {
    public PlayerMainDeskHandler() {
        super(EntityType.PLAYER, EntityType.MAINDESK);
    }

    Entity question;
    UserAction action = new UserAction("player1 Down") {
        @Override
        protected void onAction() {
            getSceneService().pushSubScene(new PuzzleSubScene());
        }
    };

    protected void onCollisionBegin(Entity player, Entity desk) {
        question = entityBuilder()
                .at(desk.getX() - 30, desk.getY())
                .view("Q.png")
                .buildAndAttach();
        player.getComponent(PlayerComponent.class).setIsNearDesk(true);
    }

    protected void onCollisionEnd(Entity player, Entity desk) {
        player.getComponent(PlayerComponent.class).setIsNearDesk(false);
        question.removeFromWorld();
    }
}