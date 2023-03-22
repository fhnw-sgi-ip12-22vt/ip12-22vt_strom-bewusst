package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class MicrowaveComponent extends Component {

    private final int sequenceNumber;
    public MicrowaveComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
}
