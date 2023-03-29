package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class FridgeComponent extends Component {

    private final int sequenceNumber;
    public FridgeComponent(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }

}
