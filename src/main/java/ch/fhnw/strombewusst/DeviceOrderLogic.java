package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DeviceOrderLogic {

    private List<DeviceOrderDevices> devices = new ArrayList<>();
    private DeviceOrderDevices[] solution;

    private DeviceOrderDevices[] answer;

    private int index = 0;

    private int size;

    public DeviceOrderLogic(int size){this.size = size;}

    public void initDevices() {
        devices = Arrays.stream(FXGL.getAssetLoader().loadJSON("json/devices.json", DeviceOrderDevices[].class).get()).toList();
        buildSolution();
    }

    public void buildSolution(){
        Random random = new Random();
        Deque<DeviceOrderDevices> deviceSet = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            int randomNum = random.nextInt(devices.size());

            while (deviceSet.contains(devices.get(randomNum))) {
                randomNum = random.nextInt(devices.size());
            }
            deviceSet.add(devices.get(randomNum));
        }

        answer = new DeviceOrderDevices[size];
        solution = deviceSet.stream()
            .sorted((x,y)->x.place()-y.place())
            .toArray(DeviceOrderDevices[]::new);
    }

    public Set<DeviceOrderDevices> getDevices(){
        return Arrays.stream(solution).collect(Collectors.toSet());
    }

    public void addAnswer(DeviceOrderDevices d){
        if(index < answer.length){answer[index++]=d;}
    }

    public boolean[] compareAnswerSolution(){
        boolean[] correctAtIndex = new boolean[solution.length];
        for(int i = 0; i < solution.length; i++){
            if(answer[i].place() == solution[i].place()){correctAtIndex[i]=true;}
        }
        return correctAtIndex;
    }

}
