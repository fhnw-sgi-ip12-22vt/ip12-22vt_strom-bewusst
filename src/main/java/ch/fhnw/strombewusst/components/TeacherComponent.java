package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class TeacherComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel animIdle;
    private boolean physicsReady = false;
    private PhysicsComponent physics;
    public TeacherComponent() {
        animIdle = new AnimationChannel(
                image("teacher-animation.png"),
                5,
                50,
                70,
                Duration.seconds(1.5),
                0,
                1
        );

        texture = new AnimatedTexture(animIdle);
    }
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(32, 32));
        texture.loopAnimationChannel(animIdle);
        entity.getViewComponent().addChild(texture);

        physics.setOnPhysicsInitialized(() -> physicsReady = true);
    }

}
