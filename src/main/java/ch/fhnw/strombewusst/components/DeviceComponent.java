package ch.fhnw.strombewusst.components;

import com.almasb.fxgl.entity.component.Component;
public class DeviceComponent extends Component {

    int deviceNum;
    public DeviceComponent(int deviceNum) {
        this.deviceNum = deviceNum;
    }
    public int getDeviceNum() {
        return deviceNum;
    }
}
