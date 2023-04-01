package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceOrderLogic {

    public List<DeviceOrderDevices> devices = new ArrayList<>();

    public DeviceOrderLogic(){
        initQuestions();
    }

    public void initQuestions() {
        devices = Arrays.stream(FXGL.getAssetLoader().loadJSON("json/devices.json", DeviceOrderDevices[].class).get()).toList();
    }
}
