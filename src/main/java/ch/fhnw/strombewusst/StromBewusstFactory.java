package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;


/**
 * This class defines our entities. The methods in this class get called by FXGL when using FXGL.spawn()
 */
public class StromBewusstFactory implements EntityFactory {
    @Spawns("emptyRoom")
    public Entity newRoom(SpawnData data) {
        String textureName = data.get("textureName");

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);

        return entityBuilder(data)
            .view(textureName)
            .with(physics)
            .bbox(new HitBox(new Point2D(20, 0), BoundingShape.box(0, FXGL.getAppHeight())))
            .bbox(new HitBox(new Point2D(0, 50), BoundingShape.box(FXGL.getAppWidth(), 0)))
            .bbox(new HitBox(new Point2D(890, 0), BoundingShape.box(0, FXGL.getAppHeight())))
            .bbox(new HitBox(new Point2D(0, 690), BoundingShape.box(FXGL.getAppWidth(), 0)))
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
                .bbox(new HitBox(new Point2D(6, -2), BoundingShape.box(52, 10)))
                .with(new PhysicsComponent())
                .with(new DeskComponent(data.get("deskNum")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("empty-desk")
    public Entity newEmptyDesk(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DESK)
                .view("empty-desk.png")
                .bbox(new HitBox(new Point2D(6, -2), BoundingShape.box(52, 10)))
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
                .bbox(new HitBox(new Point2D(6, 0), BoundingShape.box(90, 10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("bookshelf")
    public Entity newBookshelf(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.BOOKSHELF)
                .view("bookshelf.png")
                .bbox(new HitBox(new Point2D(0, 40), BoundingShape.box( 95, 20)))
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
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("prev-door")
    public Entity newPrevDoor(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PREVDOOR)
                .view("prev-door.png")
                .zIndex(1)
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

    @Spawns("plug")
    public Entity newPlug(SpawnData data) {
        return entityBuilder(data)
                .view("plug-red.png")
                .build();
    }
}
