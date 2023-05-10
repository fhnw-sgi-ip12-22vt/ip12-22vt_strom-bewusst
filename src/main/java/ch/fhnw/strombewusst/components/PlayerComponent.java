package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

/**
 * This class handles the behaviour of player entities. This includes movement and animation.
 */
public class PlayerComponent extends Component {
    private static final int PLAYER_SPEED = 300;

    private final int playerNum;
    private boolean physicsReady = false;


    private PhysicsComponent physics;

    private final AnimatedTexture texture;
    private final AnimationChannel animIdle, animWalk;

    public PlayerComponent(int playerNum) {
        this.playerNum = playerNum;
        animIdle = new AnimationChannel(
                image("player" + playerNum + ".png"),
                5,
                42,
                70,
                Duration.seconds(1.5),
                0,
                1
        );
        animWalk = new AnimationChannel(
                image("player" + playerNum + ".png"),
                5,
                42,
                70,
                Duration.seconds(0.8),
                1,
                4
        );

        texture = new AnimatedTexture(animIdle);
    }

    public PlayerComponent() {
        playerNum = 0;

        texture = null;
        animIdle = null;
        animWalk = null;
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 32));
        texture.loopAnimationChannel(animIdle);
        entity.getViewComponent().addChild(texture);

        physics.setOnPhysicsInitialized(() -> physicsReady = true);
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMoving()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    /**
     * Applies a velocity to the player entity to move it right.
     */
    public void moveRight() {
        if (!physicsReady) {
            return;
        }
        if (physics.isMovingY()) {
            physics.setVelocityX(Math.sqrt(PLAYER_SPEED * PLAYER_SPEED / 2f));
        } else {
            physics.setVelocityX(PLAYER_SPEED);
        }
        // BUGFIX: avoid wrong-thread crash
        FXGL.runOnce(() -> getEntity().setScaleX(1), Duration.ZERO);
    }

    /**
     * Applies a velocity to the player entity to move it left.
     */
    public void moveLeft() {
        if (!physicsReady) {
            return;
        }
        if (physics.isMovingY()) {
            physics.setVelocityX(-Math.sqrt(PLAYER_SPEED * PLAYER_SPEED / 2f));
        } else {
            physics.setVelocityX(-PLAYER_SPEED);
        }
        FXGL.runOnce(() -> getEntity().setScaleX(-1), Duration.ZERO);
    }

    /**
     * Applies a velocity to the player entity to move it up.
     */
    public void moveUp() {
        if (!physicsReady) {
            return;
        }
        if (physics.isMovingX()) {
            physics.setVelocityY(-Math.sqrt(PLAYER_SPEED * PLAYER_SPEED / 2f));
        } else {
            physics.setVelocityY(-PLAYER_SPEED);
        }
    }

    /**
     * Applies a velocity to the player entity to move it down.
     */
    public void moveDown() {
        if (!physicsReady) {
            return;
        }
        if (physics.isMovingX()) {
            physics.setVelocityY(Math.sqrt(PLAYER_SPEED * PLAYER_SPEED / 2f));
        } else {
            physics.setVelocityY(PLAYER_SPEED);
        }
    }

    /**
     * Resets the velocity on the player entity to stop moving horizontally.
     */
    public void stopMovingX() {
        if (!physicsReady) {
            return;
        }
        physics.setVelocityX(0);
    }

    /**
     * Resets the velocity on the player entity to stop moving vertically.
     */
    public void stopMovingY() {
        if (!physicsReady) {
            return;
        }
        physics.setVelocityY(0);
    }

    public int getPlayerNum() {
        return playerNum;
    }
}







