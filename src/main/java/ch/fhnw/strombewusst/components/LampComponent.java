package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;;

public class LampComponent extends Component {

    private final int sequenceNumber;
    public LampComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }
}