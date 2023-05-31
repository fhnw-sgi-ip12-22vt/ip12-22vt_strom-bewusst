package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.DeviceOrderDevice;
import ch.fhnw.strombewusst.DeviceOrderLogic;
import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.ui.UIHelper;
import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.animation.Interpolator;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
 * Defines the layout of our device order puzzle sub-scene. It gets rendered on top of the main menu, when the player
 * gets to the main desk in the room button 1 is pressed.
 */
public class DeviceOrderSubScene extends SubScene {
    private final Map<ImageType, Texture> currentTextures = new HashMap<>();
    private final Map<ImageType, DeviceOrderDevice> currentDevices = new HashMap<>();
    private final DeviceOrderLogic deviceOrderLogic;
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
    private int falseAnswer = 0;
    private HBox popUp;

    public DeviceOrderSubScene(DeviceOrderLogic deviceOrderLogic) {
        this.deviceOrderLogic = deviceOrderLogic;

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
        playerOne.getStyleClass().add("message");
        HBox playerTwo = getTextBox("Player 2", BoxType.PLAYERTWO, Color.BLACK, FontWeight.BOLD);
        playerTwo.getStyleClass().add("message");
        HBox answerQueue = getTextBox("Eingabe", BoxType.QUEUEINPUT, Color.BLACK, FontWeight.BOLD);
        answerQueue.getStyleClass().add("message");

        HBox scoreLabel = UIHelper.createScoreLabel(FXGL.geto("score"), 950, 50);
        HBox timerLabel = UIHelper.createTimerLabel(FXGL.geto("timer"), 950, 115);

        popUp = new HBox();
        popUp.setPrefWidth(320);
        popUp.setAlignment(Pos.CENTER);
        popUp.setTranslateX(950);
        popUp.setTranslateY(270);

        getContentRoot().getChildren().addAll(bg, inputsVBox, response, playerOne, playerTwo,
                scoreLabel, timerLabel, popUp);

        buildDeviceOrder();
        inputs();
    }

    HBox getTextBox(String txt, BoxType type, Color color, FontWeight font) {
        Text text = new Text(txt);
        text.setFont(Font.font("Press Start 2P", font, 15));
        text.setFill(color);
        HBox box = new HBox(text);
        box.setTranslateX(type.x);
        box.setTranslateY(type.y);
        return box;
    }

    void setImage(DeviceOrderDevice device, ImageType type) {
        Texture texture = getAssetLoader().loadTexture(device.image());
        texture.setFitHeight(96);
        texture.setFitWidth(96 / texture.getHeight() * texture.getWidth());
        texture.setTranslateX(type.x);
        texture.setTranslateY(type.y);
        getContentRoot().getChildren().addAll(texture);
        currentTextures.put(type, texture);
        currentDevices.put(type, device);
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
                deviceOrderLogic.clearAnswerQueue();
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ESCAPE);
    }

    /**
     * Resets all answers.
     */
    public void resetAnswers() {
        clearDeviceOrder();
        deviceOrderLogic.clearAnswerQueue();
        buildDeviceOrder();
    }

    /**
     * Checks the puzzle and resets it if there are incorrect answers or shows the next puzzle.
     */
    public void checkAnswers() {
        cleanPopUp();

        boolean[] solution = deviceOrderLogic.compareAnswerSolution();
        Set<DeviceOrderDevice> falseDevice = new HashSet<>();
        String msg = "";

        for (int i = 0; i < solution.length; i++) {
            if (!solution[i]) {
                falseDevice.add(currentDevices.get(queue[i]));
            }
        }

        if (falseDevice.isEmpty()) {
            DeviceOrderLogic logic = deviceOrderLogic;

            int score = FXGL.<Score>geto("score").increaseScoreByDeviceOrder(falseAnswer);

            Text scoreLabel = FXGL.getUIFactoryService()
                    .newText("+ " + score, Color.LIMEGREEN, FontType.UI, 22);

            getContentRoot().getChildren().add(scoreLabel);

            FXGL.animationBuilder()
                    .duration(Duration.seconds(1))
                    .translate(scoreLabel)
                    .from(new Point2D(1100, 80))
                    .to(new Point2D(1100, 5))
                    .buildAndPlay(this);

            FXGL.animationBuilder()
                    .duration(Duration.seconds(1))
                    .fadeOut(scoreLabel)
                    .buildAndPlay(this);

            Text text = FXGL.getUIFactoryService().newText("RICHTIG", Color.LIMEGREEN, FontType.UI, 36);
            popUp.getChildren().add(text);

            FXGL.animationBuilder()
                    .delay(Duration.seconds(1.5))
                    .duration(Duration.seconds(1))
                    .fadeOut(popUp)
                    .buildAndPlay(this);

            logic.setRoundsLeft(logic.getRoundsLeft() - 1);
            nextQueue();
        } else if (falseDevice.stream().anyMatch(Objects::isNull)) {
            Text text = FXGL.getUIFactoryService().newText("NICHT KOMPLETT", Color.CRIMSON, FontType.UI, 20);
            popUp.getChildren().add(text);

            FXGL.animationBuilder()
                    .delay(Duration.seconds(1.5))
                    .duration(Duration.seconds(1))
                    .fadeOut(popUp)
                    .buildAndPlay(this);
        } else {
            for (DeviceOrderDevice d : falseDevice) {
                msg += d.device() + "\n";
            }

            Text text = FXGL.getUIFactoryService().newText(msg, Color.CRIMSON, FontType.UI, 11);
            text.setLineSpacing(4);
            popUp.getChildren().add(text);
            popUp.opacityProperty().set(1);

            falseAnswer++;
        }
    }

    /**
     * Adds the provided device to the queue.
     *
     * @param player The player selecting a device. 1 or 2
     * @param colour The colour selected (0=Red, 1=Yellow, 2=Blue)
     */
    public void setDevice(int player, int colour) {
        ImageType type = plugMap[player - 1][colour];
        cleanPopUp();
        int index = deviceOrderLogic.getIndex();

        if (currentDevices.get(type) != null) {
            DeviceOrderDevice device = currentDevices.get(type);

            new AnimationBuilder()
                    .duration(Duration.seconds(0.5))
                    .interpolator(Interpolator.EASE_BOTH)
                    .translate(currentTextures.get(type))
                    .to(new Point2D(queue[index].x, queue[index].y))
                    .buildAndPlay(this);

            currentDevices.put(type, null);
            currentDevices.put(queue[index], device);

            deviceOrderLogic.addAnswer(device);
        }
    }

    /**
     * clears the puzzle UI.
     */
    public void clearDeviceOrder() {
        getContentRoot().getChildren().removeAll(currentTextures.values());
    }

    private void cleanPopUp() {
        popUp.getChildren().clear();
    }

    private void nextQueue() {
        if (deviceOrderLogic.isDeviceOrderDone()) {
            deviceOrderLogic.setDoorOpen(true);
            getSceneService().popSubScene();
        } else {
            clearDeviceOrder();
            falseAnswer = 0;
            deviceOrderLogic.buildSolution();
            buildDeviceOrder();
        }
    }

    /**
     * Builds the puzzle UI.
     */
    public void buildDeviceOrder() {
        List<DeviceOrderDevice> devices = deviceOrderLogic.getQueue();

        List<ImageType> types = Arrays.stream(ImageType.values())
                .filter(x -> x.toString().charAt(0) == 'P')
                .toList();

        if (devices.size() == types.size()) {
            for (int i = 0; i < devices.size(); i++) {
                setImage(devices.get(i), types.get(i));
            }
        }
    }

    enum BoxType {
        CONTROLS(950, 380, 30),
        RESPONSE(950, 210, 30),
        PLAYERONE(65, 35, 30),
        PLAYERTWO(65, 290, 30),
        QUEUEINPUT(65, 545, 30),
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
}
