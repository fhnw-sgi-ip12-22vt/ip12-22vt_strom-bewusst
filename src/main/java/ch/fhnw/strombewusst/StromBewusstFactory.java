package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import java.util.HashMap;

/**
 * This class defines our entities. The methods in this class get called by FXGL when using FXGL.spawn()
 */
public class StromBewusstFactory implements EntityFactory {
    @Spawns("emptyRoom")
    public Entity spawnBackground(SpawnData data) {
        return entityBuilder(data)
                .view("emptyroom-with-sidebar.png")
                .zIndex(-100)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(physics)
                .with(new PlayerComponent(data.get("playerNum")))
                .bbox(new HitBox(BoundingShape.box(40, 64)))
                .with(new CollidableComponent(true))
                .zIndex(100)
                .build();
    }

    @Spawns("desk")
    public Entity newDesk(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DESK)
                .view("desk.png")
                .bbox(new HitBox(new Point2D(12, 0), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new DeskComponent(data.get("deskNum")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("main-desk")
    public Entity newMainDesk(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.MAINDESK)
                .view("main-desk.png")
                .bbox(new HitBox(new Point2D(12, 0), BoundingShape.box(80, 10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DOOR)
                .view("door.png")
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(64, 50)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);

        return entityBuilder(data)
                .with(physics)
                .bbox(new HitBox(BoundingShape.box(data.get("width"), data.get("height"))))
                .build();
    }

    @Spawns("keyPrompt")
    public Entity newPrompt(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.KEY_PROMPT)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }


    @Spawns("message")
    public Entity newMessage(SpawnData data){
        HashMap<Integer,String> msgMap = new HashMap<>(){
            {
                put(0,"msg1.png");
                put(1,"msg2.png");
                put(2,"msg1.png");
                put(3,"msg2.png");
                put(4,"msg1.png");
                put(5,"msg2.png");
                put(6,"msg1.png");
                put(7,"msg2.png");
                put(8,"msg1.png");
                put(9,"msg2.png");
                put(10,"msg1.png");
                put(11,"msg2.png");
                put(12,"msg1.png");
                put(13,"msg2.png");
                put(14,"msg1.png");
            }
        };
        return entityBuilder(data)
                .type(EntityType.MESSAGE)
                .viewWithBBox(msgMap.get(data.get("msgNum")))
                .with(new CollidableComponent(true))
                .build();
    }
}
