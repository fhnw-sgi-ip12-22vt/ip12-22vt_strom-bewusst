package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;

/**
 * Implements the behaviour of player entities.
 */
public class PlayerComponent extends Component {
    private static final int PLAYER_SPEED = 300;

    private boolean physicsReady = false;

    private final PhysicsComponent physics;

    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    private final ObjectProperty<Point2D> velocity = new SimpleObjectProperty<>(Point2D.ZERO);

    /**
     * returns the velocity property
     * @return the velocity property
     */
    public ObjectProperty<Point2D> velocityProperty() {
        return velocity;
    }

    public PlayerComponent(PhysicsComponent physics) {
        this.physics = physics;
        physics.setOnPhysicsInitialized(() -> physicsReady = true);
    }

    @Override
    public void onUpdate(double tpf) {
        if (!physicsReady) {
            return;
        }

        if (!physics.getLinearVelocity().equals(velocity.get())) {
            physics.setLinearVelocity(velocity.get());
        }
    }

    /**
     * Creates a velocity vector for the player according to the moving... booleans
     * @return the direction velocity vector
     */
    private Point2D getVelocity() {
        double velocityX = 0;
        double velocityY = 0;

        if (movingRight) {
            velocityX = PLAYER_SPEED;
        } else if (movingLeft) {
            velocityX = -PLAYER_SPEED;
        }

        if (movingDown) {
            velocityY = PLAYER_SPEED;
        } else if (movingUp) {
            velocityY = -PLAYER_SPEED;
        }

        if ((movingRight || movingLeft) && (movingUp || movingDown)) {
            double diagonalMultiplier = Math.sqrt(2) / 2d;
            velocityX *= diagonalMultiplier;
            velocityY *= diagonalMultiplier;
        }

        return new Point2D(velocityX, velocityY);
    }

    /**
     * Applies a velocity to the player entity to move it right.
     */
    public void moveRight() {
        movingRight = true;
        movingLeft = false;
        velocity.set(getVelocity());
    }

    /**
     * Applies a velocity to the player entity to move it left.
     */
    public void moveLeft() {
        movingLeft = true;
        movingRight = false;
        velocity.set(getVelocity());
    }

    /**
     * Applies a velocity to the player entity to move it up.
     */
    public void moveUp() {
        movingUp = true;
        movingDown = false;
        velocity.set(getVelocity());
    }

    /**
     * Applies a velocity to the player entity to move it down.
     */
    public void moveDown() {
        movingDown = true;
        movingUp = false;
        velocity.set(getVelocity());
    }

    /**
     * Resets the velocity on the player entity to stop moving horizontally.
     */
    public void stopMovingX() {
        movingRight = false;
        movingLeft = false;
        velocity.set(getVelocity());
    }

    /**
     * Resets the velocity on the player entity to stop moving vertically.
     */
    public void stopMovingY() {
        movingUp = false;
        movingDown = false;
        velocity.set(getVelocity());
    }
}
