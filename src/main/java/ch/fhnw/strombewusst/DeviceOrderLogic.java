package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
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
    private static final int QUEUESIZE = 6;
    private int index;

    private int size;

    private int roundsLeft;

    //private boolean doorOpen = false;
    private boolean doorOpen = true; //for development of room 3

    public DeviceOrderLogic(int roundsTotal) {
        this.roundsLeft = roundsTotal;
        this.size = roundsTotal;
    }

    /**
     * Initializes the devices, by loading the needed data from the JSON file.
     */
    public void initDevices() {
        devices = Arrays.stream(FXGL.getAssetLoader().loadJSON("json/devices.json", DeviceOrderDevices[].class).get())
                .toList();
        trackPassedDevices = Stream.iterate(0, i -> i + 1)
                .limit(devices.size())
                .collect(Collectors.toSet());
        buildSolution();
    }

    public boolean isDeviceOrderDone() {
        return !(roundsLeft > 0);
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }

    /**
     * Randomly generates a puzzle and it's solution.
     */
    public void buildSolution() {
        if (trackPassedDevices.size() >= QUEUESIZE) {
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
                    .sorted(Comparator.comparingInt(DeviceOrderDevices::place))
                    .toArray(DeviceOrderDevices[]::new);
            index = 0;
        }
    }

    public List<DeviceOrderDevices> getDevices() {
        return Arrays.stream(solution).unordered().collect(Collectors.toList());
    }

    public int getSize() {
        return size;
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public void setRoundsLeft(int roundsLeft) {
        this.roundsLeft = roundsLeft;
    }

    /**
     * Adds a device to the answer queue.
     * @param d The device to add to the queue
     */
    public void addAnswer(DeviceOrderDevices d) {
        if (index < answer.length) {
            answer[index++] = d;
        }
    }

    /**
     * Clears the answer queue.
     */
    public void clearAnswerQueue() {
        index = 0;
        answer = new DeviceOrderDevices[QUEUESIZE];
    }

    public int getIndex() {
        return index < answer.length ? index : index - 1;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    /**
     * Compares the current queue to the solution.
     * @return a boolean array containing true for the indexes with the correct answers and false for teh indexes with
     *         incorrect ones
     */
    public boolean[] compareAnswerSolution() {
        boolean[] correctAtIndex = new boolean[solution.length];
        for (int i = 0; i < solution.length; i++) {
            if (answer[i] != null && answer[i].place() == solution[i].place()) {
                correctAtIndex[i] = true;
            }
        }
        return correctAtIndex;
    }

}
