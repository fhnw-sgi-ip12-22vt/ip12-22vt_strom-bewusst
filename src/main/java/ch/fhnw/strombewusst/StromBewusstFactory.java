package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class StromBewusstFactory implements EntityFactory {
    @Spawns("emptyRoom")
    public Entity spawnBackground(SpawnData data){
        return FXGL.entityBuilder(data)
                .view("emptyroom-with-sidebar.png")
                .zIndex(-100)
                .buildAndAttach();
      }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(physics)
                .with(new PlayerComponent(data.get("playerNum")))
                .bbox(new HitBox(BoundingShape.box(40,64)))
                .with(new CollidableComponent(true))
                .zIndex(100)
                .buildAndAttach();
    }

    @Spawns("desk")
    public Entity newDesk(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.DESK)
                .view("desk.png")
                .bbox(new HitBox(BoundingShape.box(40,10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(1)
                .buildAndAttach();
    }

    @Spawns("keyPrompt")
    public Entity newPrompt(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.KEY_PROMPT)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
}
