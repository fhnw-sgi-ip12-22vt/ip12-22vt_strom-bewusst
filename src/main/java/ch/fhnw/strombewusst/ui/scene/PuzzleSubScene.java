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
    private final int mainBoxY = 60;
    private final int firstBoxX = 260;
    private final int firstBoxY = 300;
    private final int secondBoxX = 260;
    private final int secondBoxY = 425;
    private final int thirdBoxX = 260;
    private final int thirdBoxY = 550;
    private QuizLogic quiz;
    private HBox[] currentQuiz;
    private Texture textureAnswerP1;
    private Texture textureAnswerP2;

    private Texture answerPopUp;


    public PuzzleSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground.png");
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

        String inputs = "PLAYER 1 \nROT: 4 \nGRÜN: 5 \nBLAU: 6 \n \nPLAYER 2 \nROT: 7 \nGRÜN: 8 \nBLAU: 9";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(25);

        getContentRoot().getChildren().addAll(bg, backHBox, inputsHBox);
        quiz = new QuizLogic(10);
        quiz.incQuestNum();
        currentQuiz = buildQuiz(quiz.getQustNum());
        inputs();
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                textureAnswerP1 = getAssetLoader().loadTexture("plug-red.png");
                textureAnswerP1.setTranslateX(1012);
                textureAnswerP1.setTranslateY(136);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                quiz.setAnswerP1("RED");
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                textureAnswerP1 = getAssetLoader().loadTexture("plug-green.png");
                textureAnswerP1.setTranslateX(1012);
                textureAnswerP1.setTranslateY(136);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                quiz.setAnswerP1("GREEN");
            }
        }, KeyCode.DIGIT5);


        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP1);
                textureAnswerP1 = getAssetLoader().loadTexture("plug-blue.png");
                textureAnswerP1.setTranslateX(1012);
                textureAnswerP1.setTranslateY(136);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                quiz.setAnswerP1("BLUE");

            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                textureAnswerP2 = getAssetLoader().loadTexture("plug-red.png");
                textureAnswerP2.setTranslateX(1012);
                textureAnswerP2.setTranslateY(487);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                quiz.setAnswerP2("RED");
            }
        }, KeyCode.DIGIT7);


        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                textureAnswerP2 = getAssetLoader().loadTexture("plug-green.png");
                textureAnswerP2.setTranslateX(1012);
                textureAnswerP2.setTranslateY(487);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                quiz.setAnswerP2("GREEN");
            }
        }, KeyCode.DIGIT8);


        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().removeAll(textureAnswerP2);
                textureAnswerP2 = getAssetLoader().loadTexture("plug-blue.png");
                textureAnswerP2.setTranslateX(1012);
                textureAnswerP2.setTranslateY(487);
                getContentRoot().getChildren().addAll(textureAnswerP2);
                quiz.setAnswerP2("BLUE");
            }
        }, KeyCode.DIGIT9);

        getInput().addAction(new UserAction("resetAnswers") {
            @Override
            protected void onActionBegin() {
                quiz.resetAnswers();
                getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
            }
        }, KeyCode.E);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {

                if(answerPopUp!=null){getContentRoot().getChildren().removeAll(answerPopUp);}

                if (quiz.checkAnswer()) {
                    StromBewusst.SCORE.increaseScore(1);
                    System.out.println(StromBewusst.SCORE.getScore());

                    answerPopUp = getAssetLoader().loadTexture("richtig.png");
                    answerPopUp.setTranslateX(640);
                    answerPopUp.setTranslateY(100);
                    getContentRoot().getChildren().addAll(answerPopUp);

                    nextQuestion();
                } else {
                    answerPopUp = getAssetLoader().loadTexture("falsch.png");
                    answerPopUp.setTranslateX(640);
                    answerPopUp.setTranslateY(100);
                    getContentRoot().getChildren().addAll(answerPopUp);

                    quiz.resetAnswers();
                    getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
                }
            }
        }, KeyCode.Q);


    }

    HBox buildTextbox(int question, int num) {
        Text box = new Text(quiz.getText(question, num));
        box.getStyleClass().add("message");
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
        if (quiz.quizDone()) {
            StromBewusst.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            quiz.incQuestNum();
            currentQuiz = buildQuiz(quiz.getQustNum());
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
