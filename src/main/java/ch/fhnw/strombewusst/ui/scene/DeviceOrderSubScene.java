package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.DeviceOrderDevices;
import ch.fhnw.strombewusst.QuizQuestion;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class defines the layout of our device sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class DeviceOrderSubScene extends SubScene {
    enum BoxType {
        STERRING(950,380,30),
        RESPONSE(950,210,30),
        PLAYERONE(65,25,30),
        PLAYERTWO(65,280,30),
        QUEUEINPUT(65,530,30);
        final int x, y, width;

        BoxType(int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.width = width;
        }
    }

    enum ImageType {
        PLAYERONERED(140,90),
        PLAYERONEGREEN(425,90),
        PLAYERONEBLUE(710,90),
        PLAYERTWORED(140,350),
        PLAYERTWOGREEN(425,350),
        PLAYERTWOBLUE(710,350),
        QUEUEFIRST(70,590),
        QUEUESECOND(230,590),
        QUEUETHIRD(365,590),
        QUEUEFOURTH(500,590),
        QUEUEFIFTH(625,590),
        QUEUESIXTH(750,590);
        final int x, y;

        ImageType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Map<ImageType,Texture> currentTextures = new HashMap<ImageType,Texture>();
    private Map<ImageType,DeviceOrderDevices> currentDevices = new HashMap<ImageType,DeviceOrderDevices>();

    private ImageType[] queue = {
        ImageType.QUEUEFIRST,
        ImageType.QUEUESECOND,
        ImageType.QUEUETHIRD,
        ImageType.QUEUEFOURTH,
        ImageType.QUEUEFIFTH,
        ImageType.QUEUESIXTH
    };

    public DeviceOrderSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/deviceorderbackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        //TODO set steering according to controller
        String inputs = "SORTIERE NACH KWH VERBRAUCH \nVON VIEL NACH WENIG" +
            "\nPLAYER ONE {ROT: 4 ,GRÜN: 5 ,BLAU: 6} " +
            "\nPLAYER TWO {ROT: 7 ,GRÜN: 8 ,BLAU: 9} " +
            "\nFALSCH: 0 -> 3P" +
            "\nFALSCH: 1 -> 2P" +
            "\nFALSCH:>1 -> 1P" +
            "\nÜBERPRÜFEN: Q" +
            "\nLÖSCHEN: E" +
            "\nSCHLIESSEN: ESC";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(410);


        HBox steering = getTextBox("Steuerung",BoxType.STERRING,Color.BLACK,FontWeight.BOLD);
        HBox response = getTextBox("Rückmeldung",BoxType.RESPONSE, Color.BLACK,FontWeight.BOLD);
        HBox playerone = getTextBox("Player 1",BoxType.PLAYERONE,Color.BLACK,FontWeight.BOLD);
        HBox playertwo = getTextBox("Player 2",BoxType.PLAYERTWO,Color.BLACK,FontWeight.BOLD);
        HBox answerqueue = getTextBox("Eingabe",BoxType.QUEUEINPUT,Color.BLACK,FontWeight.BOLD);
        getContentRoot().getChildren().addAll(bg,steering,response,playerone,playertwo,answerqueue,inputsHBox);

        buildDeviceOrder();
        inputs();
    }

    HBox getTextBox(String txt, BoxType type, Color color, FontWeight font){
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
        currentTextures.put(type,texture);
        currentDevices.put(type,device);
    }

    void deleteImage(ImageType type){
        getContentRoot().getChildren().removeAll(currentTextures.get(type));
        currentDevices.put(type,null);
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONERED,queue[index]);
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONEGREEN,queue[index]);
            }
        }, KeyCode.DIGIT5);

        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERONEBLUE,queue[index]);
            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWORED,queue[index]);
            }
        }, KeyCode.DIGIT7);

        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWOGREEN,queue[index]);
            }
        }, KeyCode.DIGIT8);

        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                int index = StromBewusst.DEVICES.getIndex();
                setDevice(ImageType.PLAYERTWOBLUE,queue[index]);
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
        boolean[] solution = StromBewusst.DEVICES.compareAnswerSolution();
    }

    public void setDevice(ImageType from, ImageType to) {
        if(currentDevices.get(from) != null){
            DeviceOrderDevices device = currentDevices.get(from);
            deleteImage(from);
            setImage(device,to);
            StromBewusst.DEVICES.addAnswer(device);
        }
    }

    void cleanPopUp() {
        /*if (answerPopUp != null) {
            getContentRoot().getChildren().removeAll(answerPopUp);
            answerPopUp = null;
        }*/
    }

    private HBox buildTextbox(String text, PuzzleSubScene.BoxType type) {
       /* Text box = new Text(text);
        box.setWrappingWidth(type.width);
        box.getStyleClass().add("small_title");
        HBox hBox = new HBox(box);
        hBox.setTranslateX(type.x);
        hBox.setTranslateY(type.y);

        return hBox;*/
        return null;
    }

    void clearDeviceOrder() {
        getContentRoot().getChildren().removeAll(currentTextures.values());
    }

    void nextQuestion() {
        /*StromBewusst.QUIZ.nextQuestion();

        if (StromBewusst.QUIZ.quizDone()) {
            StromBewusst.QUIZ.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            currentQuiz = buildQuiz(StromBewusst.QUIZ.getQuestion());
        }*/
    }

    private void buildDeviceOrder() {
        List<DeviceOrderDevices> devices = StromBewusst.DEVICES.getDevices();
        List<ImageType> types = Arrays.stream(ImageType.values())
            .filter(x->x.toString().substring(0,1).equals("P"))
            .toList();

        if(devices.size() == types.size()){
            for(int i = 0; i < devices.size(); i++){setImage(devices.get(i),types.get(i));}
        }
    }
}
