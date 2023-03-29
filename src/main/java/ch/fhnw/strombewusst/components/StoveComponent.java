package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class StoveComponent extends Component {

    private final int sequenceNumber;
    public StoveComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }
    public int getSequenceNumber(){
        return sequenceNumber;
    }
}
