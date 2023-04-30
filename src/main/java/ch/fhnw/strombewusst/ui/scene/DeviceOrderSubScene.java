package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.DeviceOrderDevices;
import ch.fhnw.strombewusst.DeviceOrderLogic;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        CONTROLS(950, 380, 30),
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
        PLAYERONEYELLOW(425, 90),
        PLAYERONEBLUE(710, 90),
        PLAYERTWORED(140, 350),
        PLAYERTWOYELLOW(425, 350),
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

    private final ImageType[][] plugMap = {
        {ImageType.PLAYERONERED, ImageType.PLAYERONEYELLOW, ImageType.PLAYERONEBLUE},
        {ImageType.PLAYERTWORED, ImageType.PLAYERTWOYELLOW, ImageType.PLAYERTWOBLUE}
    };

    private final ImageType[] queue = {
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

        Texture selectButton = getAssetLoader().loadTexture("red-button-icon-single.png", 48, 48);
        Text selectText = new Text("Antworten prüfen");
        selectText.getStyleClass().add("message");
        HBox selectHBox = new HBox(selectButton, selectText);
        selectHBox.setAlignment(Pos.CENTER_LEFT);
        selectHBox.setSpacing(20);

        Texture backButton = getAssetLoader().loadTexture("blue-button-icon-single.png", 48, 48);
        Text backText = new Text("Zurück");
        backText.getStyleClass().add("message");
        HBox backHBox = new HBox(backButton, backText);
        backHBox.setAlignment(Pos.CENTER_LEFT);
        backHBox.setSpacing(20);

        VBox inputsVBox = new VBox(selectHBox, backHBox);
        inputsVBox.setTranslateX(950);
        inputsVBox.setTranslateY(410);

        HBox response = getTextBox("Rückmeldung", BoxType.RESPONSE, Color.BLACK, FontWeight.BOLD);
        HBox playerOne = getTextBox("Player 1", BoxType.PLAYERONE, Color.BLACK, FontWeight.BOLD);
        HBox playerTwo = getTextBox("Player 2", BoxType.PLAYERTWO, Color.BLACK, FontWeight.BOLD);
        HBox answerQueue = getTextBox("Eingabe", BoxType.QUEUEINPUT, Color.BLACK, FontWeight.BOLD);

        getContentRoot().getChildren().addAll(bg, inputsVBox, response, playerOne, playerTwo, answerQueue);

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
        texture.setFitHeight(96);
        texture.setFitWidth(96 / texture.getHeight() * texture.getWidth());
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
                setDevice(1, 0);
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Yellow1 Button") {
            @Override
            protected void onActionBegin() {
                setDevice(1, 1);
            }
        }, KeyCode.DIGIT5);

        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                setDevice(1, 2);
            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                setDevice(2, 0);
            }
        }, KeyCode.DIGIT7);

        getInput().addAction(new UserAction("Yellow2 Button") {
            @Override
            protected void onActionBegin() {
                setDevice(2, 1);
            }
        }, KeyCode.DIGIT8);

        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                setDevice(2, 2);
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
                resetAnswers();
            }
        }, KeyCode.E);

        getInput().addAction(new UserAction("exit") {
            @Override
            protected void onActionBegin() {
                FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").clearAnswerQueue();
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ESCAPE);
    }

    /**
     * Resets all answers.
     */
    public void resetAnswers() {
        clearDeviceOrder();
        FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").clearAnswerQueue();
        buildDeviceOrder();
    }

    /**
     * Checks the puzzle and resets it if there are incorrect answers or shows the next puzzle.
     */
    public void checkAnswers() {
        cleanPopUp();
        boolean[] solution = FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").compareAnswerSolution();
        Set<DeviceOrderDevices> falseDevice = new HashSet<>();
        String msg = "";

        for (int i = 0; i < solution.length; i++) {
            if (!solution[i]) {
                falseDevice.add(currentDevices.get(queue[i]));
            }
        }

        if (falseDevice.isEmpty()) {
            DeviceOrderLogic logic = FXGL.<DeviceOrderLogic>geto("deviceOrderLogic");

            if (StromBewusst.SCORE.getQueueSolved() < logic.getSize()) {
                int increase = falseAnswer == 0 ? 3 : (falseAnswer == 1 ? 2 : 1);
                StromBewusst.SCORE.increaseScoreByDeviceOrder(increase);
            }
            msg = "RICHTIG";
            popUp = getTextBox(msg, BoxType.POPUP, Color.GREEN, FontWeight.SEMI_BOLD);
            getContentRoot().getChildren().addAll(popUp);
            logic.setRoundsLeft(logic.getRoundsLeft() - 1);
            nextQueue();
        } else if (falseDevice.stream().anyMatch(Objects::isNull)) {
            popUp = getTextBox("Nicht alle Geräte sortiert", BoxType.POPUP, Color.RED, FontWeight.SEMI_BOLD);
            getContentRoot().getChildren().addAll(popUp);
            falseAnswer++;
        } else {
            for (DeviceOrderDevices d : falseDevice) {
                msg += d.device() + "\n";
            }
            popUp = getTextBox(msg, BoxType.POPUP, Color.RED, FontWeight.SEMI_BOLD);
            getContentRoot().getChildren().addAll(popUp);
            falseAnswer++;
        }
    }

    /**
     * Adds the provided device to the queue.
     * @param player The player selecting a device. 1 or 2
     * @param colour The colour selected (0=Red, 1=Yellow, 2=Blue)
     */
    public void setDevice(int player, int colour) {
        ImageType type = plugMap[player - 1][colour];
        cleanPopUp();
        int index = FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").getIndex();

        if (currentDevices.get(type) != null) {
            DeviceOrderDevices device = currentDevices.get(type);
            deleteImage(type);
            setImage(device, queue[index]);
            FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").addAnswer(device);
        }
    }

    /**
     * clears the puzzle UI.
     */
    public void clearDeviceOrder() {
        getContentRoot().getChildren().removeAll(currentTextures.values());
        getContentRoot().getChildren().removeAll(scoreboard);
    }

    private void cleanPopUp() {
        if (popUp != null) {
            getContentRoot().getChildren().removeAll(popUp);
            popUp = null;
        }
    }

    private void nextQueue() {
        if (FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").isDeviceOrderDone()) {
            FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").setDoorOpen(true);
            getSceneService().popSubScene();
        } else {
            clearDeviceOrder();
            falseAnswer = 0;
            FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").buildSolution();
            buildDeviceOrder();
        }
    }

    /**
     * Builds the puzzle UI.
     */
    public void buildDeviceOrder() {
        List<DeviceOrderDevices> devices = FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").getDevices();
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
