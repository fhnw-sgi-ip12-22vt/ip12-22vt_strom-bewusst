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
import java.util.stream.Stream;

public class DeviceOrderLogic {

    private List<DeviceOrderDevices> devices = new ArrayList<>();
    private DeviceOrderDevices[] solution;
    private DeviceOrderDevices[] answer;
    private Set<Integer> trackPassedDevices;
    private final int QUEUESIZE = 6;
    private int index = 0;
    private int roundsTotal;


    public DeviceOrderLogic(int roundsTotal){this.roundsTotal = roundsTotal;}

    public void initDevices() {
        devices = Arrays.stream(FXGL.getAssetLoader().loadJSON("json/devices.json", DeviceOrderDevices[].class).get()).toList();
        trackPassedDevices = Stream.iterate(0,i->i+1)
            .limit(devices.size())
            .collect(Collectors.toSet());
        buildSolution();
    }

    public void buildSolution(){
        if(trackPassedDevices.size() >= QUEUESIZE) {
            Random random = new Random();
            Deque<DeviceOrderDevices> deviceSet = new LinkedList<>();

            for (int i = 0; i < QUEUESIZE; i++) {
                int randomNum = random.nextInt(devices.size());
                while (!trackPassedDevices.contains(randomNum)) {
                    randomNum = random.nextInt(devices.size());
                }
                deviceSet.add(devices.get(randomNum));
                trackPassedDevices.remove(randomNum);
            }
            answer = new DeviceOrderDevices[QUEUESIZE];
            solution = deviceSet.stream()
                .sorted((x, y) -> x.place() - y.place())
                .toArray(DeviceOrderDevices[]::new);
        }
    }

    public List<DeviceOrderDevices> getDevices(){
        return Arrays.stream(solution).unordered().collect(Collectors.toList());
    }

    public void addAnswer(DeviceOrderDevices d){
        if(index < answer.length){answer[index++]=d;}
    }

    public void deleteAnswerQueue(){index=0;answer = new DeviceOrderDevices[QUEUESIZE];}

    public int getIndex(){return index < answer.length ? index : index - 1;}

    public boolean[] compareAnswerSolution(){
        boolean[] correctAtIndex = new boolean[solution.length];
        for(int i = 0; i < solution.length; i++){
            if(answer[i].place() == solution[i].place()){correctAtIndex[i]=true;}
        }

        /*System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Answer: " + Arrays.toString(answer));*/
        return correctAtIndex;
    }

}
