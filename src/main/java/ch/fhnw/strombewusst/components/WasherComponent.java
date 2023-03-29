package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class WasherComponent extends Component {

    private final int sequenceNumber;
    public WasherComponent(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }
}
