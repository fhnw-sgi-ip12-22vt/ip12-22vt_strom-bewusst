package ch.fhnw.strombewusst;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    private int speedX = 0;
    private int speedY = 0;
    private boolean colliding;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public PlayerAnimationComponent(int playerNum) {
        animIdle = new AnimationChannel(FXGL.image("player" + playerNum + ".png"), 4, 64, 64, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(FXGL.image("player" + playerNum + ".png"), 4, 64, 64, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 32));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

        //Bewegung
        entity.translateX(speedX * tpf);
        if(colliding){
            entity.translateX((-((speedX)*tpf)));



        }

        entity.translateY(speedY * tpf);
        if(colliding){
            entity.translateY((-((speedY)*tpf)));


        }



        //Sprite Animation
        if (speedX != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speedX = (int) (speedX * 0.9);

            if (FXGLMath.abs(speedX) < 1) {
                speedX = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
        if (speedY != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speedY = (int) (speedY * 0.9);

            if (FXGLMath.abs(speedY) < 1) {
                speedY = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speedX = 150;
        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speedX = -150;
        getEntity().setScaleX(-1);
    }

    public void moveUp() {
        speedY = -150;
    }

    public void moveDown() {
        speedY = 150;
    }

    public void setColliding(boolean value){
        colliding=value;
    }
}





