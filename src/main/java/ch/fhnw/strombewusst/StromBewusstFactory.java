package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StromBewusstFactory implements EntityFactory {

@Spawns("emptyRoom")
    public Entity spawnBackground(SpawnData data){
        return FXGL.entityBuilder(data)
                .view("emptyroom.png")
                .zIndex(-100)
                .buildAndAttach();
      }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("player1-right.png")
                .with(new PlayerComponent())
                .zIndex(100)
                .buildAndAttach();
    }

    @Spawns("desk")
    public Entity desk(SpawnData data) {
        return FXGL.entityBuilder(data)
                .at(300,300)
                .viewWithBBox("desk.png")
                .zIndex(1)
                .buildAndAttach();
    }
}
