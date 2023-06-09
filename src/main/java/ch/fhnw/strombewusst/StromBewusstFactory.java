package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.random;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;


/**
 * Defines our entities. The methods in this class get called by FXGL when using FXGL.spawn()
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
        int playerNum = data.get("playerNum");

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        PlayerComponent playerComponent = new PlayerComponent(physics);

        AnimationChannel animIdle = new AnimationChannel(
                FXGL.image("player" + playerNum + ".png"),
                5,
                42,
                70,
                Duration.seconds(1.5),
                0,
                1
        );
        AnimationChannel animWalk = new AnimationChannel(
                FXGL.image("player" + playerNum + ".png"),
                5,
                42,
                70,
                Duration.seconds(0.8),
                1,
                4
        );
        AnimatedTexture texture = new AnimatedTexture(animIdle);

        Entity player = entityBuilder(data)
                .type(EntityType.PLAYER)
                .with(physics)
                .with(playerComponent)
                .bbox(new HitBox(BoundingShape.box(40, 64)))
                .with(new CollidableComponent(true))
                .zIndex(100)
                .with("playerNum", playerNum)
                .build();

        texture.loopAnimationChannel(animIdle);
        player.getViewComponent().addChild(texture);

        playerComponent.velocityProperty().addListener(((observable, oldValue, newValue) -> {
            if (Point2D.ZERO.equals(newValue)) {
                texture.loopAnimationChannel(animIdle);
            } else if (newValue.getX() > 0) {
                texture.loopAnimationChannel(animWalk);
                texture.setScaleX(1);
            } else {
                texture.loopAnimationChannel(animWalk);
                texture.setScaleX(-1);
            }
        }));

        return player;
    }

    @Spawns("buttonicon")
    public Entity newButtonIcon(SpawnData data) {
        AnimationChannel animation = new AnimationChannel(
                image("red-button-icon.png"),
                2, 32, 32,
                Duration.seconds(1), 0, 1
        );
        AnimatedTexture texture = new AnimatedTexture(animation);
        texture.loopAnimationChannel(animation);
        texture.loop();

        return entityBuilder(data)
                .type(EntityType.BUTTON)
                .view(texture)
                .zIndex(110)
                .build();
    }

    @Spawns("desk")
    public Entity newDesk(SpawnData data) {
        int rnd = random(1, 7);
        return entityBuilder(data)
                .type(EntityType.DESK)
                .view("desk" + rnd + ".png")
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(40, 10)))
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
                .bbox(new HitBox(new Point2D(6, 0), BoundingShape.box(52, 10)))
                .with(new PhysicsComponent())
                .with(new DeskComponent(data.get("deskNum")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("main-desk")
    public Entity newMainDesk(SpawnData data) {
        String roomnum = data.get("room");
        return entityBuilder(data)
                .type(EntityType.MAINDESK)
                .view("main-desk" + roomnum + ".png")
                .bbox(new HitBox(new Point2D(6, roomnum.equals("2") ? 20 : 0), BoundingShape.box(100, 10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("bookshelf")
    public Entity newBookshelf(SpawnData data) {
        int rnd = random(1, 3);
        return entityBuilder(data)
                .type(EntityType.BOOKSHELF)
                .view("bookshelf" + rnd + ".png")
                .bbox(new HitBox(new Point2D(0, 47), BoundingShape.box(95, 20)))
                .with(new PhysicsComponent())
                .with(new DeskComponent(data.get("deskNum")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        Texture closedDoorTexture = FXGL.getAssetLoader().loadTexture("door.png");
        Texture openDoorTexture = FXGL.getAssetLoader().loadTexture("door-open.png");

        Entity door = entityBuilder(data)
                .type(EntityType.DOOR)
                .view(closedDoorTexture)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(64, 50)))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .with("open", false)
                .build();

        ViewComponent doorViewComponent = door.getViewComponent();
        door.getProperties().booleanProperty("open")
                .addListener(((observable, oldValue, newValue) -> {
                    doorViewComponent.clearChildren();
                    doorViewComponent.addChild(Boolean.TRUE.equals(newValue) ? openDoorTexture : closedDoorTexture);
                }));

        door.setProperty("open", !(Config.IS_RELEASE) || Config.IS_DEMO);

        return door;
    }

    @Spawns("prev-door")
    public Entity newPrevDoor(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PREVDOOR)
                .view("prev-door.png")
                .zIndex(1)
                .build();
    }

    @Spawns("fridge")
    public Entity newFridge(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("fridge.png")
                .bbox(new HitBox(new Point2D(12, 62), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new DeviceComponent(data.get("fridge")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("stove")
    public Entity newStove(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("stove.png")
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new DeviceComponent(data.get("stove")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("washing-machine")
    public Entity newWasher(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("washingmachine.png")
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new DeviceComponent(data.get("washing-machine")))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .build();
    }

    @Spawns("ps5")
    public Entity newPS5(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("PS5.png")
                .bbox(new HitBox(new Point2D(12, 12), BoundingShape.box(40, 40)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("ps5")))
                .scale(1.5, 1.5)
                .zIndex(1)
                .build();
    }

    @Spawns("microwave")
    public Entity newMicrowave(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .bbox(new HitBox(new Point2D(7, 30), BoundingShape.box(50, 32)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("microwave")))
                .view("Mikrowelle.png")
                .zIndex(110)
                .build();
    }

    @Spawns("television")
    public Entity newTelevision(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .bbox(new HitBox(new Point2D(6, 42), BoundingShape.box(90, 32)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("television")))
                .view("Fernseher.png")
                .zIndex(110)
                .build();
    }

    @Spawns("router")
    public Entity newRouter(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .bbox(new HitBox(new Point2D(8, 15), BoundingShape.box(50, 50)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("router")))
                .view("WLAN-Router.png")
                .scale(1.5, 1.5)
                .zIndex(110)
                .build();
    }

    @Spawns("lamp")
    public Entity newLamp(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .bbox(new HitBox(new Point2D(20, 20), BoundingShape.box(50, 50)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("lamp")))
                .view("lamp.png")
                .zIndex(200)
                .build();
    }

    @Spawns("smartphone")
    public Entity newSmartphone(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(100, 100)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("smartphone")))
                .view("smartphone.png")
                .scale(0.5, 0.5)
                .zIndex(2)
                .build();
    }

    @Spawns("cabinet")
    public Entity newCabinet(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.CABINET)
                .view("cabinet.png")
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("coffeemachine")
    public Entity newCoffee(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("coffeemachine.png")
                .bbox(new HitBox(new Point2D(12, 12), BoundingShape.box(50, 50)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("coffeemachine")))
                .scale(1.5, 1.5)
                .zIndex(110)
                .build();
    }

    @Spawns("vacuum")
    public Entity newVacuum(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("vacuum.png")
                .bbox(new HitBox(new Point2D(40, 50), BoundingShape.box(40, 15)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("vacuum")))
                .zIndex(1)
                .build();
    }

    @Spawns("laptop")
    public Entity newLaptop(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("laptop.png")
                .bbox(new HitBox(new Point2D(8, 15), BoundingShape.box(60, 50)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("laptop")))
                .zIndex(3)
                .build();
    }

    @Spawns("dishwasher")
    public Entity newDishwasher(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("dishwasher.png")
                .bbox(new HitBox(new Point2D(12, -2), BoundingShape.box(40, 10)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("dishwasher")))
                .zIndex(1)
                .build();
    }

    @Spawns("computer")
    public Entity newComputer(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.DEVICE)
                .view("computer.png")
                .bbox(new HitBox(new Point2D(12, 12), BoundingShape.box(40, 40)))
                .with(new CollidableComponent(true))
                .with(new DeviceComponent(data.get("computer")))
                .scale(1.5, 1.5)
                .zIndex(200)
                .build();
    }

    @Spawns("teacher")
    public Entity newTeacher(SpawnData data) {
        AnimationChannel animation = new AnimationChannel(
                image("teacher.png"),
                2, 50, 72,
                Duration.seconds(0.6), 0, 1
        );
        AnimatedTexture texture = new AnimatedTexture(animation);
        texture.loopAnimationChannel(animation);
        texture.loop();

        return entityBuilder(data)
                .type(EntityType.DOOR)
                .bbox(new HitBox(BoundingShape.box(50, 20)))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .view(texture)
                .zIndex(90)
                .with("open", true)
                .build();
    }
    @Spawns("outside")
    public Entity newOutside(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        String texture = data.get("textureName");
        physics.setBodyType(BodyType.STATIC);

        return entityBuilder(data)
                .view(texture)
                .with(physics)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(0, FXGL.getAppHeight()))) //rand links
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(FXGL.getAppWidth(), 250))) //rand oben
                .bbox(new HitBox(new Point2D(FXGL.getAppWidth() - 210, 0),
                        BoundingShape.box(0, FXGL.getAppHeight()))) //rand rechts
                .bbox(new HitBox(new Point2D(0, FXGL.getAppHeight() - 20),
                        BoundingShape.box(FXGL.getAppWidth(), 0)))// rand unten
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(510, 390)))
                .bbox(new HitBox(new Point2D(565, 0), BoundingShape.box(395, 390)))
                .bbox(new HitBox(new Point2D(570, 250), BoundingShape.box(FXGL.getAppWidth() - 570, 50)))
                .bbox(new HitBox(new Point2D(0, 490), BoundingShape.box(420, 290)))
                .bbox(new HitBox(new Point2D(660, 490),
                        BoundingShape.box(695, FXGL.getAppHeight() - 490)))
                .zIndex(-100)
                .build();

    }

}
