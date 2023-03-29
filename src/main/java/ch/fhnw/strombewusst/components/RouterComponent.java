package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class RouterComponent extends Component {

    private final int sequenceNumber;
    public RouterComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }
}
