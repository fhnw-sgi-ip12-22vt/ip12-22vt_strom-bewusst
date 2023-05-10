package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceOrderLogic {
    /** The list of all devices as defined in devices.json */
    private List<DeviceOrderDevice> allDevices;
    /** The list of devices, devices that were already used in a queue get removed from this list */
    private List<DeviceOrderDevice> devices;
    /** The current queue of devices */
    private List<DeviceOrderDevice> queue;
    /** The solution of the current queue */
    private List<DeviceOrderDevice> solution;
    /** The current answer queue as ordered by the players */
    private List<DeviceOrderDevice> playerAnswer;

    /** The number of devices in a queue. Since there are 2 players with 3 plugs each this is 6 */
    private static final int QUEUESIZE = 6;
    private int roundsLeft;

    private boolean doorOpen = !(Config.IS_RELEASE) || Config.IS_DEMO;

    public DeviceOrderLogic(int roundsTotal) {
        this.roundsLeft = roundsTotal;
    }

    /**
     * Initializes the devices, by loading the needed data from the JSON file.
     */
    public void initDevices() {
        allDevices = Arrays.stream(FXGL.getAssetLoader().loadJSON(Config.DEVICES_JSON_PATH, DeviceOrderDevice[].class)
                .get()).collect(Collectors.toList());

        devices = new ArrayList<>(allDevices);
        Collections.shuffle(devices);

        buildSolution();
    }

    public boolean isDeviceOrderDone() {
        return !(roundsLeft > 0);
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }

    /**
     * Randomly generates a puzzle and its solution.
     */
    public void buildSolution() {
        if (devices.size() < QUEUESIZE) {
            // not enough devices to avoid using any multiple times - reset device list
            devices = new ArrayList<>(allDevices);
            Collections.shuffle(devices);
        }

        queue = new ArrayList<>(devices.subList(0, QUEUESIZE));
        devices.removeAll(queue);

        solution = queue.stream()
                .sorted(Comparator.comparingInt(DeviceOrderDevice::consumption))
                .toList();

        if (!Config.IS_RELEASE || Config.IS_DEMO) {
            queue = solution;
        }

        playerAnswer = new ArrayList<>();
    }

    public List<DeviceOrderDevice> getQueue() {
        return queue;
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
    public void addAnswer(DeviceOrderDevice d) {
        if (playerAnswer.size() < QUEUESIZE) {
            playerAnswer.add(d);
        }
    }

    /**
     * Clears the answer queue.
     */
    public void clearAnswerQueue() {
        playerAnswer.clear();
    }

    public int getIndex() {
        return playerAnswer.size();
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
        boolean[] correctAtIndex = new boolean[QUEUESIZE];
        for (int i = 0; i < playerAnswer.size(); i++) {
            correctAtIndex[i] = playerAnswer.get(i).equals(solution.get(i));
        }

        return correctAtIndex;
    }

}
