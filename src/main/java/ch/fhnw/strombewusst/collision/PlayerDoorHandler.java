package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.DeviceOrderLogic;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.QuizLogic;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerDoorHandler extends CollisionHandler {
    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    private Entity question;

    protected void onCollisionBegin(Entity player, Entity door) {
        if (FXGL.geti("level") == 1 && FXGL.<QuizLogic>geto("quizLogic").isDoorOpen()) {
            question = FXGL.spawn("buttonicon", door.getX() + 50, door.getY() + 30);
        } else if (FXGL.geti("level") == 2 && FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").isDoorOpen()) {
            question = FXGL.spawn("buttonicon", door.getX() + 50, door.getY() + 30);
        } else {
            question = FXGL.spawn("buttonicon", door.getX() + 50, door.getY());
        }
    }

    protected void onCollisionEnd(Entity player, Entity door) {
        if (question != null) {
            question.removeFromWorld();
        }
    }
}
