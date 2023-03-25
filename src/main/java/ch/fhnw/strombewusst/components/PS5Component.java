package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class PS5Component extends Component {

    private final int sequenceNumber;
    public PS5Component(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
}
