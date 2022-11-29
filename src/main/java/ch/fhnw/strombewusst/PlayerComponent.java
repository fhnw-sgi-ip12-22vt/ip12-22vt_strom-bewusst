package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerComponent extends Component {
    private static final int PLAYER_SPEED = 300;
    private PhysicsComponent physics;

    private final AnimatedTexture texture;
    private final AnimationChannel animIdle, animWalk;

    public PlayerComponent(int playerNum) {
        animIdle = new AnimationChannel(
                image("player" + playerNum + ".png"),
                4,
                42,
                64,
                Duration.seconds(1),
                1,
                1
        );
        animWalk = new AnimationChannel(
                image("player" + playerNum + ".png"),
                4,
                42,
                64,
                Duration.seconds(1),
                0,
                3
        );

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 32));
        entity.getViewComponent().addChild(texture);
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

    public void moveRight() {
        physics.setVelocityX(PLAYER_SPEED);
        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        physics.setVelocityX(-PLAYER_SPEED);
        getEntity().setScaleX(-1);
    }

    public void moveUp() {
        physics.setVelocityY(-PLAYER_SPEED);
    }

    public void moveDown() {
        physics.setVelocityY(PLAYER_SPEED);
    }

    public void stopMovingX() {
        physics.setVelocityX(0);
    }

    public void stopMovingY() {
        physics.setVelocityY(0);
    }
}




