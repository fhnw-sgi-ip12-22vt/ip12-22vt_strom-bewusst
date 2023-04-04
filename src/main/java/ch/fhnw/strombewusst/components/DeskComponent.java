package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class DeskComponent extends Component {

    private final int deskNum;

    public DeskComponent(int deskNum) {
        this.deskNum = deskNum;
    }

    public int getDeskNum() {
        return deskNum;
    }
}
