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
                world.create("main-desk", new SpawnData(470, 53).put("textureName", "main-desk2.png")),

                // Haushaltgeräte
                world.create("fridge", new SpawnData(5, 55).put("fridge", 19)), //positioned
                //world.create("lamp", new SpawnData(4*x,y).put("lamp", 1)), missing texture
                world.create("television", new SpawnData(90, 230).put("television", 21)), //positioned
                world.create("router", new SpawnData(323, 580).put("router", 22)), //positioned
                world.create("microwave", new SpawnData(760, 412).put("microwave", 24)), //positioned
                //world.create("smartphone", new SpawnData(2*x,3*y).put("smartphone", 1)), missing texture
                world.create("ps5", new SpawnData(205, 240).put("ps5", 25)), //positioned

                //first row
                world.create("cabinet", new SpawnData(22, 75)),
                world.create("cabinet", new SpawnData(86, 75)),
                world.create("stove", new SpawnData(150, 75).put("stove", 20)), // TODO: Fix Text
                world.create("cabinet", new SpawnData(214, 75)),
                world.create("stove", new SpawnData(278, 75).put("stove", 20)), // TODO: Fix Text
                world.create("cabinet", new SpawnData(342, 75)),
                world.create("stove", new SpawnData(406, 75).put("stove", 20)), // TODO: Fix Text

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
                world.create("cabinet", new SpawnData(764, 258)),
                world.create("cabinet", new SpawnData(828, 258)),

                //third row
                world.create("cabinet", new SpawnData(86, 441)),
                world.create("cabinet", new SpawnData(150, 441)),
                world.create("cabinet", new SpawnData(214, 441)),
                world.create("cabinet", new SpawnData(278, 441)),
                world.create("cabinet", new SpawnData(342, 441)),
                world.create("cabinet", new SpawnData(406, 441)),
                world.create("cabinet", new SpawnData(470, 441)),
                world.create("cabinet", new SpawnData(534, 441)),
                world.create("washing-machine", new SpawnData(764, 441).put("washing-machine", 23)),
                world.create("washing-machine", new SpawnData(828, 441).put("washing-machine", 23)),

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
                world.create("washing-machine", new SpawnData(764, 625).put("washing-machine", 23)),
                world.create("washing-machine", new SpawnData(828, 625).put("washing-machine", 23))
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

    public String initText() {
        return "room2_deviceinfo.txt";
    }
}
