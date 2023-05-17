package ch.fhnw.strombewusst.rooms;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.ui.UIHelper;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;

import java.util.List;

/**
 * Defines the entities and cutscene of room 1.
 */
public class Room1 implements Room {
    private final Entity player1;
    private final Entity player2;
    private final Entity door;

    private final List<Entity> entities;

    public Room1() {
        GameWorld world = FXGL.getGameWorld();

        player1 = world.create("player", new SpawnData(566, 300).put("playerNum", 1));
        player2 = world.create("player", new SpawnData(694, 300).put("playerNum", 2));
        door = world.create("door", new SpawnData(618, 6));

        entities = List.of(
                player1,
                player2,

                world.create("emptyRoom", new SpawnData()
                        .put("textureName", "room1-with-sidebar.png")),
                door,

                world.create("main-desk", new SpawnData(320, 75).put("room", "1")),
                world.create("desk", new SpawnData(103, 267).put("deskNum", 0)),
                world.create("desk", new SpawnData(264, 267).put("deskNum", 1)),
                world.create("desk", new SpawnData(425, 267).put("deskNum", 2)),
                world.create("desk", new SpawnData(586, 267).put("deskNum", 3)),
                world.create("desk", new SpawnData(747, 267).put("deskNum", 4)),
                world.create("desk", new SpawnData(103, 405).put("deskNum", 5)),
                world.create("desk", new SpawnData(264, 405).put("deskNum", 6)),
                world.create("desk", new SpawnData(425, 405).put("deskNum", 7)),
                world.create("desk", new SpawnData(586, 405).put("deskNum", 8)),
                world.create("desk", new SpawnData(747, 405).put("deskNum", 9)),
                world.create("desk", new SpawnData(103, 543).put("deskNum", 10)),
                world.create("desk", new SpawnData(264, 543).put("deskNum", 11)),
                world.create("desk", new SpawnData(425, 543).put("deskNum", 12)),
                world.create("desk", new SpawnData(586, 543).put("deskNum", 13)),
                world.create("desk", new SpawnData(747, 543).put("deskNum", 14)),

                world.create("empty-desk", new SpawnData(830, 75).put("deskNum", 15)),
                world.create("empty-desk", new SpawnData(510, 75).put("deskNum", 16)),
                world.create("empty-desk", new SpawnData(20, 625).put("deskNum", 17)),

                world.create("bookshelf", new SpawnData(150, 18).put("deskNum", 18)),
                world.create("bookshelf", new SpawnData(40, 18).put("deskNum", 19))
        );
    }

    public Level getLevel() {
        return new Level(Config.WIDTH, Config.HEIGHT, entities);
    }

    @Override
    public void onStarted() {
        if (Config.IS_RELEASE || Config.IS_DEMO) {
            List<String> lines = FXGL.getAssetLoader().loadText(Config.TUTORIAL_CUTSCENE_PATH);
            Cutscene cutscene = new Cutscene(lines);
            UIHelper.showCutsceneWithButton(cutscene, true);
        }
    }

    public Entity getPlayer1() {
        return player1;
    }

    public Entity getPlayer2() {
        return player2;
    }

    public Entity getDoor() {
        return door;
    }
}
