package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.ui.scene.LeaderboardSubScene;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class PlayerMainDeskHandler extends CollisionHandler {

    public PlayerMainDeskHandler() {
        super(EntityType.PLAYER, EntityType.MAINDESK);
    }

    protected void onCollisionBegin(Entity player, Entity desk){
        getSceneService().pushSubScene(new PuzzleSubScene());
    }
}
