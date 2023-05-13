package ch.fhnw.strombewusst.rooms;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.StromBewusst;
import ch.fhnw.strombewusst.Timer;
import ch.fhnw.strombewusst.ui.UIHelper;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;

import java.util.List;

/**
 * Defines the entities and cutscene of the last room (outside the school).
 */
public class OutsideRoom implements Room {
    private final Entity player1;
    private final Entity player2;
    private final Entity teacher;

    private final List<Entity> entities;

    public OutsideRoom() {
        GameWorld world = FXGL.getGameWorld();
        player1 = world.create("player", new SpawnData(500, 350).put("playerNum", 1));
        player2 = world.create("player", new SpawnData(550, 350).put("playerNum", 2));
        teacher = world.create("teacher", new SpawnData(460, 620).put("state", 0));

        entities = List.of(
                player1,
                player2,
                teacher,
                world.create("outside", new SpawnData().put("textureName", "outside.png")
        ));
    }

    @Override
    public Level getLevel() {
        return new Level(Config.WIDTH, Config.HEIGHT, entities);
    }

    @Override
    public void onStarted() {
        FXGL.<Timer>geto("timer").pause();
        ((StromBewusst) FXGL.getApp()).clearUI();

        if (Config.IS_RELEASE || Config.IS_DEMO) {
            List<String> lines = FXGL.getAssetLoader().loadText(Config.FINAL_CUTSCENE_PATH);
            Cutscene cutscene = new Cutscene(lines);
            UIHelper.showCutsceneWithButton(cutscene);
        }
    }

    @Override
    public Entity getPlayer1() {
        return player1;
    }

    @Override
    public Entity getPlayer2() {
        return player2;
    }

    @Override
    public Entity getDoor() {
        return teacher;
    }
}
