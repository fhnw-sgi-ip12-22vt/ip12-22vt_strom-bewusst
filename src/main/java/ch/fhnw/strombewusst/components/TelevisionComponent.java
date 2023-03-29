package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class TelevisionComponent extends Component {

    private final int sequenceNumber;
    public TelevisionComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }
}
