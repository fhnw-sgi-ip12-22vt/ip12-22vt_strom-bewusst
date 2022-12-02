package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class PlugComponent extends Component {
    String plugColor;

    public PlugComponent(String plugColor) {
        this.plugColor = plugColor;
    }
}

