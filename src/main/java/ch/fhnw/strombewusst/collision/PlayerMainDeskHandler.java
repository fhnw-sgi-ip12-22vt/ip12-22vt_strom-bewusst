package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.DeviceOrderLogic;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.QuizLogic;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

/**
 * Implements the logic of collisions between a player and the main desk.
 */
public class PlayerMainDeskHandler extends CollisionHandler {
    public PlayerMainDeskHandler() {
        super(EntityType.PLAYER, EntityType.MAINDESK);
    }

    private Entity question;

    @Override
    protected void onCollisionBegin(Entity player, Entity desk) {
        if (FXGL.geti("level") == 1 && !FXGL.<QuizLogic>geto("quizLogic").quizDone()) {
            question = FXGL.spawn("buttonicon", desk.getX() + 50, desk.getY() - 32);
        } else if (FXGL.geti("level") == 2 && !FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").isDeviceOrderDone()) {
            question = FXGL.spawn("buttonicon", desk.getX() + 50, desk.getY() - 32);
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        if (question != null) {
            question.removeFromWorld();
        }
    }
}
