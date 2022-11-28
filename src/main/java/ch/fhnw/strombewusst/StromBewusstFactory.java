package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StromBewusstFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("player1-right.png")
                .with(new PlayerComponent())
                .buildAndAttach();
    }

    @Spawns("desk")
    public Entity desk(SpawnData data) {
        return FXGL.entityBuilder(data)
                .at(300,300)
                .viewWithBBox("desk.png")
                .buildAndAttach();
    }
}
