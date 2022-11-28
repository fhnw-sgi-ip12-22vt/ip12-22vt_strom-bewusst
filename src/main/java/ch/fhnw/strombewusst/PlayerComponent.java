package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component {
    public void up(double dpixels) {
        entity.setY(Math.max(entity.getY() - dpixels, 0));
    }
    public void down(double dpixels) {
        entity.setY(Math.min(entity.getY() + dpixels, FXGL.getAppHeight() - entity.getHeight()));
    }
    public void left(double dpixels) {
        entity.setX(Math.max(entity.getX() - dpixels, 0));
    }
    public void right(double dpixels) {
        entity.setX(Math.min(entity.getX() + dpixels, FXGL.getAppWidth() - entity.getWidth()));
    }
}
