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




        int x = 100;
        int y = 100;
        int xCabinet= 22;
        int yCabinet= 600;//spawnlocation of cabinet to the wall on the left
        int cabinetWidth = 62;
        int cabinetDelta = 170;

        entities = List.of(
            player1,
            player2,

            world.create("emptyRoom", new SpawnData().put("textureName", "room2-with-sidebar.png")),
            door,
            world.create("prev-door", new SpawnData(618, FXGL.getAppHeight() - 127)),
                //Geräteorderung
                world.create("main-desk", new SpawnData(770, 150)),

                //Haushaltgeräte
                world.create("fridge", new SpawnData(2*x, y).put("fridge", 19)),
                world.create("stove", new SpawnData(3*x,y).put("stove", 1)),
                world.create("lamp", new SpawnData(4*x,y).put("lamp", 1)),
                world.create("lamp", new SpawnData(5*x,y).put("lamp", 1)),
                world.create("television", new SpawnData(2*x,2*y).put("television", 1)),
                world.create("router", new SpawnData(3*x,2*y).put("router", 1)),
                world.create("washing-machine", new SpawnData(4*x,2*y).put("washing-machine", 1)),
                world.create("microwave", new SpawnData(5*x,2*y).put("microwave", 1)),
                world.create("smartphone", new SpawnData(2*x,3*y).put("smartphone", 1)),
                world.create("ps5", new SpawnData(3*x,3*y).put("ps5", 1)),

                //first row
                world.create("cabinet", new SpawnData(xCabinet, yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+cabinetWidth, yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(2*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(3*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(4*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(5*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(6*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(7*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(8*cabinetWidth), yCabinet)),

                world.create("cabinet", new SpawnData(xCabinet+(12*cabinetWidth), yCabinet)),
                world.create("cabinet", new SpawnData(xCabinet+(13*cabinetWidth), yCabinet)),


                //second row
                world.create("cabinet", new SpawnData(xCabinet+(cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(2*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(3*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(4*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(5*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(6*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(7*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(8*cabinetWidth), yCabinet-cabinetDelta)),

                world.create("cabinet", new SpawnData(xCabinet+(12*cabinetWidth), yCabinet-cabinetDelta)),
                world.create("cabinet", new SpawnData(xCabinet+(13*cabinetWidth), yCabinet-cabinetDelta)),

                //third row
                world.create("cabinet", new SpawnData(xCabinet+(cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(2*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(3*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(4*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(5*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(6*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(7*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(8*cabinetWidth), yCabinet-(cabinetDelta*2))),

                world.create("cabinet", new SpawnData(xCabinet+(12*cabinetWidth), yCabinet-(cabinetDelta*2))),
                world.create("cabinet", new SpawnData(xCabinet+(13*cabinetWidth), yCabinet-(cabinetDelta*2))),

                //fourth row
                world.create("cabinet", new SpawnData(xCabinet, yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+cabinetWidth, yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(2*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(3*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(4*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(5*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(6*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(7*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(8*cabinetWidth), yCabinet-(cabinetDelta*3))),

                world.create("cabinet", new SpawnData(xCabinet+(12*cabinetWidth), yCabinet-(cabinetDelta*3))),
                world.create("cabinet", new SpawnData(xCabinet+(13*cabinetWidth), yCabinet-(cabinetDelta*3)))



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
