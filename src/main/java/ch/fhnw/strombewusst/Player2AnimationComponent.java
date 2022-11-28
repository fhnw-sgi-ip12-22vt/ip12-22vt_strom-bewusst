package ch.fhnw.strombewusst;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Player2AnimationComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public Player2AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.image("player2.png"), 4, 64, 64, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(FXGL.image("player2.png"), 4, 64, 64, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 32));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

        //entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speed = 150;
        entity.translateX(5);

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;
        entity.translateX(-5);

        getEntity().setScaleX(-1);
    }

    public void moveUp() {
        speed = -150;
        entity.translateY(-5);
    }

    public void moveDown() {
        speed = 150;
        entity.translateY(5);
    }
}



