package ch.fhnw.strombewusst.rooms;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.Level;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

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

        entities = List.of(
            player1,
            player2,

            //spawn game boundaries & background
            world.create("wall", new SpawnData(20, 0).put("width", 0d).put("height", (double) getAppHeight())),
            world.create("wall", new SpawnData(0, 50).put("width", (double) getAppWidth()).put("height", 0d)),
            world.create("wall", new SpawnData(890, 0).put("width", 0d).put("height", (double) getAppHeight())),
            world.create("wall", new SpawnData(0, getAppHeight() - 30).put("width", (double) getAppWidth()).put("height", 0d)),
            world.create("emptyRoom", new SpawnData()),

            //spawn room elements
            world.create("prev-door", new SpawnData(618, getAppHeight() - 127)),
            door = world.create("door", new SpawnData(618, 6).put("state",0))
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
