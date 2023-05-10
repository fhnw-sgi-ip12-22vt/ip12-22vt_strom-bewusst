package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private static class DummyPlayerComponent extends PlayerComponent {

        @Override
        public void onAdded() {
            // Override the onAdded method, to avoid calling any view / UI related code
        }

        public boolean isPhysicsReady() {
            return true;
        }
    }

    PhysicsComponent physics;
    PhysicsWorld physicsWorld;
    Entity player;

    @BeforeEach
    void entitySetup() {
        physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        player = new Entity();
        player.setType(EntityType.PLAYER);
        player.addComponent(physics);
        player.addComponent(new DummyPlayerComponent());

        physicsWorld = new PhysicsWorld(100, 10);
        physicsWorld.setGravity(0, 0);

        physicsWorld.onEntityAdded(player);

        Point2D initialPosition = player.getPosition();
        assertEquals(new Point2D(0, 0), initialPosition);
    }

    @Test
    void playerUpTest() {
        player.getComponent(DummyPlayerComponent.class).moveUp();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        player.getComponent(DummyPlayerComponent.class).stopMovingY();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        Point2D afterMoveUpPosition = player.getPosition();

        assertEquals(new Point2D(0, -20), afterMoveUpPosition);
    }

    @Test
    void playerLeftTest() {
        try {
            player.getComponent(DummyPlayerComponent.class).moveLeft();
        } catch (kotlin.UninitializedPropertyAccessException ignored) {
            // Exception happens because of the FXGL.runOnce() call
        }
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        player.getComponent(DummyPlayerComponent.class).stopMovingX();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        Point2D afterMoveLeftPosition = player.getPosition();

        assertEquals(new Point2D(-20, 0), afterMoveLeftPosition);
    }

    @Test
    void playerDownTest() {
        player.getComponent(DummyPlayerComponent.class).moveDown();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        player.getComponent(DummyPlayerComponent.class).stopMovingY();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        Point2D afterMoveDownPosition = player.getPosition();

        assertEquals(new Point2D(0, 20), afterMoveDownPosition);
    }

    @Test
    void playerRightTest() {
        try {
            player.getComponent(DummyPlayerComponent.class).moveRight();
        } catch (kotlin.UninitializedPropertyAccessException ignored) {
            // Exception happens because of the FXGL.runOnce() call
        }
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        player.getComponent(DummyPlayerComponent.class).stopMovingX();
        physicsWorld.onUpdate(1.0);
        physics.onUpdate(1.0);
        Point2D afterMoveRightPosition = player.getPosition();

        assertEquals(new Point2D(20, 0), afterMoveRightPosition);
    }
}
