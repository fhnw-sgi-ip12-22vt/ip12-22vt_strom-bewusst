package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.DeviceOrderDevices;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;


/**
 * This class defines the layout of our device sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class DeviceOrderSubScene extends SubScene {
    enum BoxType {
        STERRING(950, 380, 30),
        RESPONSE(950, 210, 30),
        PLAYERONE(65, 25, 30),
        PLAYERTWO(65, 280, 30),
        QUEUEINPUT(65, 530, 30),
        POPUP(950, 240, 15),
        SCORETABLE(950, 30, 44);
        final int x, y, width;

        BoxType(int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.width = width;
        }
    }

    enum ImageType {
        PLAYERONERED(140, 90),
        PLAYERONEGREEN(425, 90),
        PLAYERONEBLUE(710, 90),
        PLAYERTWORED(140, 350),
        PLAYERTWOGREEN(425, 350),
        PLAYERTWOBLUE(710, 350),
        QUEUEFIRST(70, 590),
        QUEUESECOND(230, 590),
        QUEUETHIRD(365, 590),
        QUEUEFOURTH(500, 590),
        QUEUEFIFTH(625, 590),
        QUEUESIXTH(750, 590);
        final int x, y;

        ImageType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Map<ImageType, Texture> currentTextures = new HashMap<ImageType, Texture>();
    private Map<ImageType, DeviceOrderDevices> currentDevices = new HashMap<ImageType, DeviceOrderDevices>();

    private int falseAnswer = 0;

    private ImageType[] queue = {
        ImageType.QUEUEFIRST,
        ImageType.QUEUESECOND,
        ImageType.QUEUETHIRD,
        ImageType.QUEUEFOURTH,
        ImageType.QUEUEFIFTH,
        ImageType.QUEUESIXTH
    };

    private HBox popUp;
    private HBox scoreboard;

    public DeviceOrderSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/deviceorderbackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        //TODO set steering according to controller
        String inputs = "SORTIERE NACH KWH VERBRAUCH \nVON VIEL NACH WENIG"
                + "\nPLAYER ONE {ROT: 4 ,GRÜN: 5 ,BLAU: 6} "
                + "\nPLAYER TWO {ROT: 7 ,GRÜN: 8 ,BLAU: 9} "
                + "\nFALSCH: 0 -> 3P"
                + "\nFALSCH: 1 -> 2P"
                + "\nFALSCH:>1 -> 1P"
                + "\nÜBERPRÜFEN: Q"
                + "\nLÖSCHEN: E"
                + "\nSCHLIESSEN: ESC";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(410);


        HBox steering = getTextBox("Steuerung", BoxType.STERRING, Color.BLACK, FontWeight.BOLD);
        HBox response = getTextBox("Rückmeldung", BoxType.RESPONSE, Color.BLACK, FontWeight.BOLD);
        HBox playerone = getTextBox("Player 1", BoxType.PLAYERONE, Color.BLACK, FontWeight.BOLD);
        HBox playertwo = getTextBox("Player 2", BoxType.PLAYERTWO, Color.BLACK, FontWeight.BOLD);
        HBox answerqueue = getTextBox("Eingabe", BoxType.QUEUEINPUT, Color.BLACK, FontWeight.BOLD);
        getContentRoot().getChildren().addAll(bg, steering, response, playerone, playertwo, answerqueue, inputsHBox);

        buildDeviceOrder();
        inputs();
    }

    HBox getTextBox(String txt, BoxType type, Color color, FontWeight font) {
        Text text = new Text(txt);
        text.setFont(Font.font("Arial", font, type.width));
        text.setFill(color);
        HBox box = new HBox(text);
        box.setTranslateX(type.x);
        box.setTranslateY(type.y);
        return box;
    }

    void setImage(DeviceOrderDevices device, ImageType type) {
        Texture texture = getAssetLoader().loadTexture(device.image());
        texture.setTranslateX(type.x);
        texture.setTranslateY(type.y);
        getContentRoot().getChildren().addAll(texture);
        currentTextures.put(type, texture);
        currentDevices.put(type, device);
    }

    void deleteImage(ImageType type) {
        getContentRoot().getChildren().removeAll(currentTextures.get(type));
        currentDevices.put(type, null);
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONERED, queue[index]);
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONEGREEN, queue[index]);
            }
        }, KeyCode.DIGIT5);

        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONEBLUE, queue[index]);
            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWORED, queue[index]);
            }
        }, KeyCode.DIGIT7);

        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWOGREEN, queue[index]);
            }
        }, KeyCode.DIGIT8);

        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                cleanPopUp();
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWOBLUE, queue[index]);
            }
        }, KeyCode.DIGIT9);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {
                checkAnswers();
            }
        }, KeyCode.Q);

        getInput().addAction(new UserAction("resetAnswers") {
            @Override
            protected void onActionBegin() {
                clearDeviceOrder();
                StromBewusst.DEVICES.deleteAnswerQueue();
                buildDeviceOrder();
            }
        }, KeyCode.E);

        getInput().addAction(new UserAction("exit") {
            @Override
            protected void onActionBegin() {
                StromBewusst.DEVICES.deleteAnswerQueue();
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ESCAPE);
    }

    public void checkAnswers() {
        cleanPopUp();
        boolean[] solution = StromBewusst.DEVICES.compareAnswerSolution();
        Set<DeviceOrderDevices> falseDevice = new HashSet<>();
        String msg = "";

        for (int i = 0; i < solution.length; i++) {
            if (!solution[i]) {
                falseDevice.add(currentDevices.get(queue[i]));
            }
        }

        if (falseDevice.isEmpty()) {
            if (StromBewusst.SCORE.getQueueSolved() < StromBewusst.DEVICES.getSize()) {
                int increase = falseAnswer == 0 ? 3 : (falseAnswer == 1 ? 2 : 1);
                StromBewusst.SCORE.increaseScoreByDeviceOrder(increase);
            }
            msg = "RICHTIG";
            popUp = getTextBox(msg, BoxType.POPUP, Color.GREEN, FontWeight.SEMI_BOLD);
            getContentRoot().getChildren().addAll(popUp);
            nextQueue();
        } else {
            for (DeviceOrderDevices d : falseDevice) {
                msg += d.device() + "\n";
            }
            popUp = getTextBox(msg, BoxType.POPUP, Color.RED, FontWeight.SEMI_BOLD);
            getContentRoot().getChildren().addAll(popUp);
            falseAnswer++;
        }
    }

    public void setDevice(ImageType from, ImageType to) {
        if (currentDevices.get(from) != null) {
            DeviceOrderDevices device = currentDevices.get(from);
            deleteImage(from);
            setImage(device, to);
            StromBewusst.DEVICES.addAnswer(device);
        }
    }

    void clearDeviceOrder() {
        getContentRoot().getChildren().removeAll(currentTextures.values());
        getContentRoot().getChildren().removeAll(scoreboard);
    }

    void cleanPopUp() {
        if (popUp != null) {
            getContentRoot().getChildren().removeAll(popUp);
            popUp = null;
        }
    }

    void nextQueue() {
        if (StromBewusst.DEVICES.deviceOrderDone()) {
            StromBewusst.DEVICES.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearDeviceOrder();
            falseAnswer = 0;
            StromBewusst.DEVICES.buildSolution();
            buildDeviceOrder();
        }
    }

    private void buildDeviceOrder() {
        List<DeviceOrderDevices> devices = StromBewusst.DEVICES.getDevices();
        //Collections.shuffle(devices); //comment it, then you pass puzzle with key 456789
        List<ImageType> types = Arrays.stream(ImageType.values())
                .filter(x -> x.toString().charAt(0) == 'P')
                .toList();

        scoreboard = getTextBox(StromBewusst.SCORE.toString(), BoxType.SCORETABLE, Color.BLACK, FontWeight.SEMI_BOLD);
        getContentRoot().getChildren().addAll(scoreboard);

        if (devices.size() == types.size()) {
            for (int i = 0; i < devices.size(); i++) {
                setImage(devices.get(i), types.get(i));
            }
        }
    }
}
