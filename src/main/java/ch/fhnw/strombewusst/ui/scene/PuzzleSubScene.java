package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.QuizLogic;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This class defines the layout of our puzzle sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class PuzzleSubScene extends SubScene {

    private final int mainBoxX = 70;
    private final int mainBoxY = 40;
    private final int firstBoxX = 300;
    private final int firstBoxY = 300;
    private final int secondBoxX = 300;
    private final int secondBoxY = 450;
    private final int thirdBoxX = 300;
    private final int thirdBoxY = 600;

    private final int plugP1X = 925;

    private final int plugP1Y = 590;

    private final int plugP2X = 1100;

    private final int plugP2Y = 590;
    private HBox[] currentQuiz;
    private Texture textureAnswerP1;
    private Texture textureAnswerP2;

    private Texture answerPopUp;


    public PuzzleSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground2.png");
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

        String inputs = "PLAYER ONE {ROT: 4 ,GRÜN: 5 ,BLAU: 6} \nPLAYER TWO {ROT: 7 ,GRÜN: 8 ,BLAU: 9}";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(430);

        HBox score = getTextBox("Score",950,30);
        HBox steering = getTextBox("Steuerung",950,380);
        HBox response = getTextBox("Rückmeldung",950,210);
        HBox answerOne = getTextBox("Antwort 1", 955,555);
        HBox answerTwo = getTextBox("Antwort 2", 1135,555);

        getContentRoot().getChildren().addAll(bg, backHBox, inputsHBox,score,steering,response,answerOne,answerTwo);

        currentQuiz = buildQuiz(StromBewusst.QUIZ.getQustNum());
        inputs();
    }

    HBox getTextBox(String txt, int x, int y){
        Text text = new Text(txt);
        text.getStyleClass().add("small_title");
        HBox textHBox = new HBox(text);
        textHBox.setTranslateX(x);
        textHBox.setTranslateY(y);
        return textHBox;
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                cleanPopUp();
                textureAnswerP1 = getAssetLoader().loadTexture("plug-red.png");
                textureAnswerP1.setTranslateX(plugP1X);
                textureAnswerP1.setTranslateY(plugP1Y);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                StromBewusst.QUIZ.setAnswerP1("RED");
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                cleanPopUp();
                textureAnswerP1 = getAssetLoader().loadTexture("plug-green.png");
                textureAnswerP1.setTranslateX(plugP1X);
                textureAnswerP1.setTranslateY(plugP1Y);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                StromBewusst.QUIZ.setAnswerP1("GREEN");
            }
        }, KeyCode.DIGIT5);


        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                cleanPopUp();
                textureAnswerP1 = getAssetLoader().loadTexture("plug-blue.png");
                textureAnswerP1.setTranslateX(plugP1X);
                textureAnswerP1.setTranslateY(plugP1Y);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                StromBewusst.QUIZ.setAnswerP1("BLUE");

            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                cleanPopUp();
                textureAnswerP2 = getAssetLoader().loadTexture("plug-red.png");
                textureAnswerP2.setTranslateX(plugP2X);
                textureAnswerP2.setTranslateY(plugP2Y);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                StromBewusst.QUIZ.setAnswerP2("RED");
            }
        }, KeyCode.DIGIT7);


        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                cleanPopUp();
                textureAnswerP2 = getAssetLoader().loadTexture("plug-green.png");
                textureAnswerP2.setTranslateX(plugP2X);
                textureAnswerP2.setTranslateY(plugP2Y);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                StromBewusst.QUIZ.setAnswerP2("GREEN");
            }
        }, KeyCode.DIGIT8);


        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                cleanPopUp();
                textureAnswerP2 = getAssetLoader().loadTexture("plug-blue.png");
                textureAnswerP2.setTranslateX(plugP2X);
                textureAnswerP2.setTranslateY(plugP2Y);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                StromBewusst.QUIZ.setAnswerP2("BLUE");
            }
        }, KeyCode.DIGIT9);

        getInput().addAction(new UserAction("resetAnswers") {
            @Override
            protected void onActionBegin() {
                StromBewusst.QUIZ.resetAnswers();
                getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
            }
        }, KeyCode.E);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {

                if (StromBewusst.QUIZ.checkAnswer()) {
                    StromBewusst.SCORE.increaseScore(1);
                    System.out.println(StromBewusst.SCORE.getScore());

                    answerPopUp = getAssetLoader().loadTexture("richtig.png");
                    answerPopUp.setTranslateX(990);
                    answerPopUp.setTranslateY(100);
                    getContentRoot().getChildren().addAll(answerPopUp);

                    nextQuestion();
                } else {
                    answerPopUp = getAssetLoader().loadTexture("falsch.png");
                    answerPopUp.setTranslateX(990);
                    answerPopUp.setTranslateY(100);
                    getContentRoot().getChildren().addAll(answerPopUp);

                    StromBewusst.QUIZ.resetAnswers();
                    getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
                }
            }
        }, KeyCode.Q);


    }

    void cleanPopUp(){
        if(answerPopUp!=null){
            getContentRoot().getChildren().removeAll(answerPopUp);
            answerPopUp = null;
        }
    }

    HBox buildTextbox(int question, int num) {
        Text box = new Text(StromBewusst.QUIZ.getText(question, num));
        box.getStyleClass().add("small_title");
        HBox boxHBox = new HBox(box);
        if (num == 0) {
            boxHBox.setTranslateX(mainBoxX);
            boxHBox.setTranslateY(mainBoxY);
        } else if (num == 1) {
            boxHBox.setTranslateX(firstBoxX);
            boxHBox.setTranslateY(firstBoxY);
        } else if (num == 2) {
            boxHBox.setTranslateX(secondBoxX);
            boxHBox.setTranslateY(secondBoxY);
        } else {
            boxHBox.setTranslateX(thirdBoxX);
            boxHBox.setTranslateY(thirdBoxY);
        }
        return boxHBox;
    }

    void clearQuiz() {
        getContentRoot().getChildren()
                .removeAll(currentQuiz[0], currentQuiz[1], currentQuiz[2], currentQuiz[3], textureAnswerP1,
                        textureAnswerP2);
    }

    void nextQuestion() {
        if (StromBewusst.QUIZ.quizDone()) {
            StromBewusst.QUIZ.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            StromBewusst.QUIZ.nextQuestion();
            currentQuiz = buildQuiz(StromBewusst.QUIZ.getQustNum());
        }
    }

    HBox[] buildQuiz(int i) {
        HBox questionHBox = buildTextbox(i, 0);
        HBox firstHBox = buildTextbox(i, 1);
        HBox secondHBox = buildTextbox(i, 2);
        HBox thirdHBox = buildTextbox(i, 3);
        getContentRoot().getChildren().addAll(questionHBox, firstHBox, secondHBox, thirdHBox);
        HBox[] result = {questionHBox, firstHBox, secondHBox, thirdHBox};
        return result;
    }

}
