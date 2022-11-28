package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

public class StromBewusstFactory implements EntityFactory {



@Spawns("emptyRoom")
    public Entity spawnBackground(SpawnData data){
        return FXGL.entityBuilder(data)
                .view("emptyroom.png")
                .zIndex(-100)
                .buildAndAttach();
      }

    @Spawns("player1")
    public Entity player1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(StromBewusst.EntityType.PLAYER)
                .with(new Player1AnimationComponent())
                .bbox(new HitBox(BoundingShape.box(64,64)))
                .with(new CollidableComponent(true))
                .zIndex(100)
                .buildAndAttach();
    }

    @Spawns("player2")
    public Entity player2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(StromBewusst.EntityType.PLAYER)
                .with(new Player2AnimationComponent())
                .bbox(new HitBox(BoundingShape.box(64,64)))
                .with(new CollidableComponent(true))
                .zIndex(100)
                .buildAndAttach();
    }

    @Spawns("desk")
    public Entity desk(SpawnData data) {
        boolean collision = true;
        return FXGL.entityBuilder(data)
                .type(StromBewusst.EntityType.OBSTACLE)
                .at(300,300)
                .viewWithBBox("desk.png")
                .with(new CollidableComponent(true))
                .zIndex(1)
                .buildAndAttach();

    }
}
