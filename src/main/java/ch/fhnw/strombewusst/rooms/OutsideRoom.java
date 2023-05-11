package ch.fhnw.strombewusst.rooms;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.ui.scene.UIHelper;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;

import java.util.List;

public class OutsideRoom implements Room {
    private final Entity player1;
    private final Entity player2;
    private final Entity teacher;

    private final List<Entity> entities;

    public OutsideRoom() {
        GameWorld world = FXGL.getGameWorld();
        player1 = world.create("player", new SpawnData(603, 625).put("playerNum", 1));
        player2 = world.create("player", new SpawnData(656, 625).put("playerNum", 2));
        teacher = world.create("teacher", new SpawnData(500, 500).put("state", 0));

        entities = List.of(
                player1,
                player2,
                teacher,
                world.create("emptyRoom", new SpawnData().put("textureName", "outside.png")
        ));
    }

    @Override
    public Level getLevel() {
        return new Level(Config.WIDTH, Config.HEIGHT, entities);
    }

    @Override
    public void onStarted() {
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
