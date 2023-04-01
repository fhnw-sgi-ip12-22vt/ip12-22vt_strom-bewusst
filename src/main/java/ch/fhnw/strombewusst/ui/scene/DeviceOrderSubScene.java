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
        QUEUEFIRST(65,530),
        QUEUESECOND(65,530),
        QUEUETHIRD(65,530),
        QUEUEFOURTH(65,530),
        QUEUEFIFTH(65,530),
        QUEUESIXTH(65,530);
        final int x, y;

        ImageType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Map<ImageType,Texture> currentDevices = new HashMap<ImageType,Texture>();

    public DeviceOrderSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/deviceorderbackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Button btnBack = new Button("Back");
        btnBack.getStyleClass().add("main_menu_button");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> getSceneService().popSubScene());

        HBox backHBox = new HBox(btnBack);
        backHBox.setPrefWidth(getAppWidth());
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setTranslateY(getAppHeight() - 90);

        //TODO
        /*String inputs = "PLAYER ONE {ROT: 4 ,GRÜN: 5 ,BLAU: 6} \nPLAYER TWO {ROT: 7 ,GRÜN: 8 ,BLAU: 9} \nFALSCH: 0 -> 3P\nFALSCH: 1 -> 2P\nFALSCH:>1 -> 1P";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(410);
        */

        HBox steering = getTextBox("Steuerung",BoxType.STERRING,Color.BLACK,FontWeight.BOLD);
        HBox response = getTextBox("Rückmeldung",BoxType.RESPONSE, Color.BLACK,FontWeight.BOLD);
        HBox playerone = getTextBox("Player 1",BoxType.PLAYERONE,Color.BLACK,FontWeight.BOLD);
        HBox playertwo = getTextBox("Player 2",BoxType.PLAYERTWO,Color.BLACK,FontWeight.BOLD);
        HBox answerqueue = getTextBox("Eingabe",BoxType.QUEUEINPUT,Color.BLACK,FontWeight.BOLD);
        getContentRoot().getChildren().addAll(bg, backHBox,steering,response,playerone,playertwo,answerqueue);

        StromBewusst.DEVICES.initDevices();
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
        currentDevices.put(type,texture);
    }

    void deleteImage(ImageType type){
        getContentRoot().getChildren().removeAll(currentDevices.get(type));
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(1, 0);
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(1, 1);
            }
        }, KeyCode.DIGIT5);

        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(1, 2);
            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(2, 0);
            }
        }, KeyCode.DIGIT7);

        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(2, 1);
            }
        }, KeyCode.DIGIT8);

        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                setPlug(2, 2);
            }
        }, KeyCode.DIGIT9);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {
                checkAnswers();
            }
        }, KeyCode.Q);

        getInput().addAction(new UserAction("exit") {
            @Override
            protected void onActionBegin() {
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ESCAPE);
    }

    public void checkAnswers() {
        /*cleanPopUp();

        if (StromBewusst.QUIZ.checkAnswer()) {
            if (StromBewusst.SCORE.getAnswerSolved() < StromBewusst.QUIZ.getSize()) {
                int increase = falseAnswer == 0 ? 3 : (falseAnswer == 1 ? 2 : 1);
                StromBewusst.SCORE.increaseScore(increase);
            }

            Text text = new Text("RICHTIG");
            text.setStyle("-fx-font-size: 44px;");
            text.setFill(Color.GREEN);
            answerPopUp = new HBox(text);
            answerPopUp.setTranslateX(1020);
            answerPopUp.setTranslateY(250);
            getContentRoot().getChildren().addAll(answerPopUp);

            nextQuestion();
        } else {
            Text text = new Text("FALSCH");
            text.setStyle("-fx-font-size: 44px;");
            text.setFill(Color.RED);
            answerPopUp = new HBox(text);
            answerPopUp.setTranslateX(1020);
            answerPopUp.setTranslateY(250);
            getContentRoot().getChildren().addAll(answerPopUp);

            StromBewusst.QUIZ.resetAnswers();
            falseAnswer++;
            getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
        }*/
    }

    public void setPlug(int player, int colour) {
        /*if (player == 1) {
            getContentRoot().getChildren().removeAll(textureAnswerP1);
        } else if (player == 2) {
            getContentRoot().getChildren().removeAll(textureAnswerP2);
        }
        cleanPopUp();

        switch (colour) {
        case 0 -> setImagePlug("plug-red.png", player);
        case 1 -> setImagePlug("plug-green.png", player);
        case 2 -> setImagePlug("plug-blue.png", player);
        }

        if (player == 1) {
            getContentRoot().getChildren().addAll(textureAnswerP1);
            StromBewusst.QUIZ.setAnswerP1(colour);
        } else if (player == 2) {
            getContentRoot().getChildren().addAll(textureAnswerP2);
            StromBewusst.QUIZ.setAnswerP2(colour);
        }*/

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

    void clearQuiz() {
        /*getContentRoot().getChildren()
            .removeAll(currentQuiz[0], currentQuiz[1], currentQuiz[2], currentQuiz[3], textureAnswerP1,
                textureAnswerP2, scoretable);
        falseAnswer = 0;*/
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
        StromBewusst.DEVICES.buildSolution();

        List<DeviceOrderDevices> devices = StromBewusst.DEVICES.getDevices();
        List<ImageType> types = Arrays.stream(ImageType.values())
            .filter(x->x.toString().substring(0,1).equals("P"))
            .toList();

        if(devices.size() == types.size()){
            for(int i = 0; i < devices.size(); i++){setImage(devices.get(i),types.get(i));}
        }
    }
}
