package ch.fhnw.strombewusst.rooms;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;

import java.util.List;

public class Room2 implements Room {
    private final Entity player1;
    private final Entity player2;
    private final Entity door;

    private final List<Entity> entities;

    @Override
    public Level getLevel() {
        return new Level(FXGL.getAppWidth(), FXGL.getAppHeight(), entities);
    }

    public Room2() {
        GameWorld world = FXGL.getGameWorld();

        player1 = world.create("player", new SpawnData(603, 625).put("playerNum", 1));
        player2 = world.create("player", new SpawnData(656, 625).put("playerNum", 2));
        door = world.create("door", new SpawnData(618, 6).put("state", 0));

        entities = List.of(
                player1,
                player2,

                world.create("emptyRoom", new SpawnData().put("textureName", "room2-with-sidebar.png")),
                door,
                world.create("prev-door", new SpawnData(618, FXGL.getAppHeight() - 127)),
                // Geräteorderung
                world.create("main-desk", new SpawnData(470, 53).put("room", "2")),

                // Haushaltgeräte
                //world.create("lamp", new SpawnData(4*x,y).put("lamp", 1)), missing texture
                world.create("television", new SpawnData(100, 385).put("television", 4)),
                world.create("router", new SpawnData(323, 580).put("router", 6)),
                world.create("microwave", new SpawnData(405, 215).put("microwave", 9)),
                world.create("smartphone", new SpawnData(828, 75).put("smartphone", 13)),
                world.create("coffeemachine", new SpawnData(764, 625).put("coffeemachine", 14)),
                world.create("laptop", new SpawnData(214, 258).put("laptop", 11)),
                world.create("computer", new SpawnData(22, 625).put("computer", 8)),
                world.create("vacuum", new SpawnData(534, 258).put("vacuum", 12)),
                world.create("lamp", new SpawnData(534, 258).put("lamp", 1)),
                world.create("ps5", new SpawnData(210, 410).put("ps5", 7)),

                //first row
                world.create("fridge", new SpawnData(22, 11).put("fridge", 2)),
                world.create("cabinet", new SpawnData(86, 75)),
                world.create("stove", new SpawnData(150, 75).put("stove", 0)),
                world.create("cabinet", new SpawnData(214, 75)),
                world.create("stove", new SpawnData(278, 75).put("stove", 0)),
                world.create("cabinet", new SpawnData(342, 75)),
                world.create("stove", new SpawnData(406, 75).put("stove", 0)),

                world.create("cabinet", new SpawnData(764, 75)),
                world.create("cabinet", new SpawnData(828, 75)),


                //second row
                world.create("cabinet", new SpawnData(86, 258)),
                world.create("cabinet", new SpawnData(150, 258)),
                world.create("cabinet", new SpawnData(214, 258)),
                world.create("cabinet", new SpawnData(278, 258)),
                world.create("cabinet", new SpawnData(342, 258)),
                world.create("cabinet", new SpawnData(406, 258)),
                world.create("cabinet", new SpawnData(470, 258)),
                world.create("cabinet", new SpawnData(534, 258)),

                world.create("washing-machine", new SpawnData(764, 258).put("washing-machine", 3)),
                world.create("washing-machine", new SpawnData(828, 258).put("washing-machine", 3)),

                //third row
                world.create("cabinet", new SpawnData(86, 441)),
                world.create("cabinet", new SpawnData(150, 441)),
                world.create("cabinet", new SpawnData(214, 441)),
                world.create("cabinet", new SpawnData(278, 441)),
                world.create("cabinet", new SpawnData(342, 441)),
                world.create("cabinet", new SpawnData(406, 441)),
                world.create("cabinet", new SpawnData(470, 441)),
                world.create("cabinet", new SpawnData(534, 441)),

                world.create("washing-machine", new SpawnData(764, 441).put("washing-machine", 3)),
                world.create("dishwasher", new SpawnData(828, 441).put("dishwasher", 5)),

                //fourth row
                world.create("cabinet", new SpawnData(22, 625)),
                world.create("cabinet", new SpawnData(86, 625)),
                world.create("cabinet", new SpawnData(150, 625)),
                world.create("cabinet", new SpawnData(214, 625)),
                world.create("cabinet", new SpawnData(278, 625)),
                world.create("cabinet", new SpawnData(342, 625)),
                world.create("cabinet", new SpawnData(406, 625)),
                world.create("cabinet", new SpawnData(470, 625)),
                world.create("cabinet", new SpawnData(534, 625)),

                world.create("cabinet", new SpawnData(764, 625)),
                world.create("cabinet", new SpawnData(828, 625))
        );
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
        return door;
    }
}
