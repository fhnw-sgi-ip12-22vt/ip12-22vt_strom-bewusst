package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.DeviceOrderLogic;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.QuizLogic;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerDoorHandler extends CollisionHandler {
    public PlayerDoorHandler() {
        super(EntityType.PLAYER, EntityType.DOOR);
    }

    private Entity question;

    protected void onCollisionBegin(Entity player, Entity desk) {
        if (((StromBewusst) FXGL.getApp()).getLevel() == 1
                && FXGL.<QuizLogic>geto("quizLogic").isDoorOpen()) {
            question = FXGL.spawn("buttonicon", desk.getX() + 50, desk.getY() + 30);
        } else if (((StromBewusst) FXGL.getApp()).getLevel() == 2
                && FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").isDoorOpen()) {
            question = FXGL.spawn("buttonicon", desk.getX() + 50, desk.getY() + 30);
        }
    }

    protected void onCollisionEnd(Entity player, Entity desk) {
        if (question != null) {
            question.removeFromWorld();
        }
    }
}
