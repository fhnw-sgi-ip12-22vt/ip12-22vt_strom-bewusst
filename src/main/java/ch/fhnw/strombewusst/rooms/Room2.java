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




        //location parameters for cabinets
        int xCabinet= 22;
        int yCabinet= 600;
        int cabinetWidth = 62;
        int cabinetDelta = 178;

        entities = List.of(
            player1,
            player2,

            world.create("emptyRoom", new SpawnData().put("textureName", "room2-with-sidebar.png")),
            door,
            world.create("prev-door", new SpawnData(618, FXGL.getAppHeight() - 127)),
                //Geräteorderung
                world.create("main-desk", new SpawnData(765, 86).put("textureName", "main-desk2.png")),

                //Haushaltgeräte
                world.create("fridge", new SpawnData(5, 55).put("fridge", 19)), //positioned
                world.create("stove", new SpawnData(300,55).put("stove", 20)),
                //world.create("lamp", new SpawnData(4*x,y).put("lamp", 1)), missing texture
                world.create("television", new SpawnData(90,230).put("television", 21)), //positioned
                world.create("router", new SpawnData(323,580).put("router", 22)), //positioned
                world.create("washing-machine", new SpawnData(500,250).put("washing-machine", 23)), //positioned
                world.create("microwave", new SpawnData(760, 412).put("microwave", 24)), //positioned
                //world.create("smartphone", new SpawnData(2*x,3*y).put("smartphone", 1)), missing texture
                world.create("ps5", new SpawnData(205,240).put("ps5", 25)),//positioned

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

    public String initText(){
        return "room2_deviceinfo.txt";
    }
}
