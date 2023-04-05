package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.QuizLogic;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * This class defines the layout of our puzzle sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class PuzzleSubScene extends SubScene {
    enum BoxType {
        QUESTION(70, 40, 700),
        REDANSWER(300, 300, 550),
        GREENANSWER(300, 450, 550),
        BLUEANSWER(300, 600, 550);
        final int x, y, width;

        BoxType(int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.width = width;
        }
    }

    private final int plugP1X = 925;

    private final int plugP1Y = 590;

    private final int plugP2X = 1100;

    private final int plugP2Y = 590;

    private int falseAnswer = 0;
    private HBox[] currentQuiz;
    private Texture textureAnswerP1;
    private Texture textureAnswerP2;

    private HBox answerPopUp;

    private HBox scoretable;


    public PuzzleSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground2.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Button btnBack = new Button("Back");
        btnBack.getStyleClass().add("main_menu_button");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> getSceneService().popSubScene());

        Texture selectButton = getAssetLoader().loadTexture("red-button-icon-single.png", 48, 48);
        Text selectText = new Text("Antworten prüfen");
        selectText.getStyleClass().add("message");
        HBox selectHBox = new HBox(selectButton, selectText);
        selectHBox.setAlignment(Pos.CENTER_LEFT);
        selectHBox.setSpacing(20);

        Texture backButton = getAssetLoader().loadTexture("green-button-icon-single.png", 48, 48);
        Text backText = new Text("Zurück");
        backText.getStyleClass().add("message");
        HBox backHBox = new HBox(backButton, backText);
        backHBox.setAlignment(Pos.CENTER_LEFT);
        backHBox.setSpacing(20);

        VBox inputsVBox = new VBox(selectHBox, backHBox);
        inputsVBox.setTranslateX(950);
        inputsVBox.setTranslateY(410);

        HBox steering = getTextBox("Steuerung", 950, 380);
        HBox response = getTextBox("Rückmeldung", 950, 210);
        HBox answerOne = getTextBox("Antwort 1", 955, 555);
        HBox answerTwo = getTextBox("Antwort 2", 1135, 555);

        getContentRoot().getChildren().addAll(bg, inputsVBox, steering, response, answerOne, answerTwo);

        currentQuiz = buildQuiz(FXGL.<QuizLogic>geto("quizLogic").getQuestion());
        inputs();
    }

    HBox getTextBox(String txt, int x, int y) {
        Text text = new Text(txt);
        text.getStyleClass().add("small_title");
        HBox textHBox = new HBox(text);
        textHBox.setTranslateX(x);
        textHBox.setTranslateY(y);
        return textHBox;
    }

    void setImagePlug(String image, int player) {
        if (player == 1) {
            textureAnswerP1 = getAssetLoader().loadTexture(image);
            textureAnswerP1.setTranslateX(plugP1X);
            textureAnswerP1.setTranslateY(plugP1Y);
            textureAnswerP1.setScaleX(0.75);
            textureAnswerP1.setScaleY(0.75);
        } else {
            textureAnswerP2 = getAssetLoader().loadTexture(image);
            textureAnswerP2.setTranslateX(plugP2X);
            textureAnswerP2.setTranslateY(plugP2Y);
            textureAnswerP2.setScaleX(0.75);
            textureAnswerP2.setScaleY(0.75);
        }
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

        getInput().addAction(new UserAction("resetAnswers") {
            @Override
            protected void onActionBegin() {
                FXGL.<QuizLogic>geto("quizLogic").resetAnswers();
                getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
            }
        }, KeyCode.E);

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
        cleanPopUp();

        if (FXGL.<QuizLogic>geto("quizLogic").checkAnswer()) {
            if (StromBewusst.SCORE.getAnswerSolved() < FXGL.<QuizLogic>geto("quizLogic").getSize()) {
                int increase = falseAnswer == 0 ? 3 : (falseAnswer == 1 ? 2 : 1);
                StromBewusst.SCORE.increaseScoreByQuiz(increase);
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

            FXGL.<QuizLogic>geto("quizLogic").resetAnswers();
            falseAnswer++;
            getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
        }
    }

    public void setPlug(int player, int colour) {
        if (player == 1) {
            getContentRoot().getChildren().removeAll(textureAnswerP1);
        } else if (player == 2) {
            getContentRoot().getChildren().removeAll(textureAnswerP2);
        }
        cleanPopUp();

        switch (colour) {
        case 0 -> setImagePlug("plug-red.png", player);
        case 1 -> setImagePlug("plug-green.png", player);
        case 2 -> setImagePlug("plug-blue.png", player);
        default -> System.out.println("unknown colour!");
        }

        if (player == 1) {
            getContentRoot().getChildren().addAll(textureAnswerP1);
            FXGL.<QuizLogic>geto("quizLogic").setAnswerP1(colour);
        } else if (player == 2) {
            getContentRoot().getChildren().addAll(textureAnswerP2);
            FXGL.<QuizLogic>geto("quizLogic").setAnswerP2(colour);
        }

    }

    void cleanPopUp() {
        if (answerPopUp != null) {
            getContentRoot().getChildren().removeAll(answerPopUp);
            answerPopUp = null;
        }
    }

    private HBox buildTextbox(String text, BoxType type) {
        Text box = new Text(text);
        box.setWrappingWidth(type.width);
        box.getStyleClass().add("small_title");
        HBox hBox = new HBox(box);
        hBox.setTranslateX(type.x);
        hBox.setTranslateY(type.y);

        return hBox;
    }

    void clearQuiz() {
        getContentRoot().getChildren()
                .removeAll(currentQuiz[0], currentQuiz[1], currentQuiz[2], currentQuiz[3], textureAnswerP1,
                        textureAnswerP2, scoretable);
        falseAnswer = 0;
    }

    void nextQuestion() {
        QuizLogic quiz = FXGL.geto("quizLogic");
        quiz.nextQuestion();

        if (quiz.quizDone()) {
            quiz.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            currentQuiz = buildQuiz(quiz.getQuestion());
        }
    }

    private HBox[] buildQuiz(QuizQuestion question) {
        HBox questionHBox = buildTextbox(question.question(), BoxType.QUESTION);
        HBox firstHBox = buildTextbox(question.answerOptions()[0], BoxType.REDANSWER);
        HBox secondHBox = buildTextbox(question.answerOptions()[1], BoxType.GREENANSWER);
        HBox thirdHBox = buildTextbox(question.answerOptions()[2], BoxType.BLUEANSWER);

        scoretable = StromBewusst.SCORE.pushScore(950, 30);

        getContentRoot().getChildren().addAll(questionHBox, firstHBox, secondHBox, thirdHBox, scoretable);
        return new HBox[] {questionHBox, firstHBox, secondHBox, thirdHBox};
    }
}
