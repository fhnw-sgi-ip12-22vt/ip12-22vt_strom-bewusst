package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;

public class SmartPhoneComponent extends Component {

    private final int sequenceNumber;
    public SmartPhoneComponent(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }
}
