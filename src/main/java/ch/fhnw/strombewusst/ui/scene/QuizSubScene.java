package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.QuizLogic;
import ch.fhnw.strombewusst.QuizQuestion;
import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.ui.UIHelper;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * This class defines the layout of our puzzle sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class QuizSubScene extends SubScene {
    enum BoxType {
        QUESTION(90, 70, 670),
        REDANSWER(310, 320, 500),
        YELLOWANSWER(310, 470, 500),
        BLUEANSWER(310, 620, 500);

        final int x;
        final int y;
        final int width;

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

    private int falseAnswerCount = 0;
    private Node[] currentQuiz;
    private Texture textureAnswerP1;
    private Texture textureAnswerP2;
    private final QuizLogic quizLogic;

    private HBox answerPopUp;

    public QuizSubScene(QuizLogic quizLogic) {
        this.quizLogic = quizLogic;
        quizLogic.resetAnswers();

        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Texture selectButton = getAssetLoader().loadTexture("red-button-icon-single.png", 48, 48);
        Text selectText = FXGL.getUIFactoryService().newText("Antworten prüfen", Color.BLACK, 14);
        HBox selectHBox = new HBox(selectButton, selectText);
        selectHBox.setAlignment(Pos.CENTER_LEFT);
        selectHBox.setSpacing(15);

        Texture backButton = getAssetLoader().loadTexture("blue-button-icon-single.png", 48, 48);
        Text backText = FXGL.getUIFactoryService().newText("Zurück", Color.BLACK, 14);
        backText.getStyleClass().add("message");
        HBox backHBox = new HBox(backButton, backText);
        backHBox.setAlignment(Pos.CENTER_LEFT);
        backHBox.setSpacing(15);

        VBox inputsVBox = new VBox(selectHBox, backHBox);
        inputsVBox.setTranslateX(950);
        inputsVBox.setTranslateY(420);

        Text responseTitle = UIHelper.getUITitle("Rückmeldung", new Point2D(950, 230));
        Text controlsTitle = UIHelper.getUITitle("Steuerung", new Point2D(950, 405));
        Text answerOneTitle = UIHelper.getUITitle("Spieler 1", new Point2D(950, 585), 13);
        Text answerTwoTitle = UIHelper.getUITitle("Spieler 2", new Point2D(1130, 585), 13);

        HBox scoreLabel = UIHelper.createScoreLabel(FXGL.geto("score"), 950, 50);
        HBox timerLabel = UIHelper.createTimerLabel(FXGL.geto("timer"), 950, 115);

        getContentRoot().getChildren().addAll(bg, inputsVBox, controlsTitle, responseTitle, answerOneTitle,
                answerTwoTitle, scoreLabel, timerLabel);

        currentQuiz = buildQuiz(quizLogic.getQuestion());
        inputs();
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

        getInput().addAction(new UserAction("Yellow1 Button") {
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

        getInput().addAction(new UserAction("Yellow2 Button") {
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
                quizLogic.resetAnswers();
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
                quizLogic.resetAnswers();
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ESCAPE);
    }

    /**
     * Checks the puzzle and resets it if there are incorrect answers or shows the next puzzle.
     */
    public void checkAnswers() {
        cleanPopUp();

        answerPopUp = new HBox();
        answerPopUp.setPrefWidth(320);
        answerPopUp.setAlignment(Pos.CENTER);
        answerPopUp.setTranslateX(940);
        answerPopUp.setTranslateY(265);

        if (quizLogic.checkAnswer()) {
            Text text = FXGL.getUIFactoryService().newText("RICHTIG", Color.LIMEGREEN, FontType.UI, 36);
            answerPopUp.getChildren().add(text);

            int score = FXGL.<Score>geto("score").increaseScoreByQuiz(falseAnswerCount);

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

            nextQuestion();
        } else {
            Text text = FXGL.getUIFactoryService().newText("FALSCH", Color.CRIMSON, FontType.UI, 36);
            answerPopUp.getChildren().add(text);

            falseAnswerCount++;
            getContentRoot().getChildren().removeAll(textureAnswerP1, textureAnswerP2);
        }

        getContentRoot().getChildren().add(answerPopUp);

        FXGL.animationBuilder()
                .delay(Duration.seconds(1.5))
                .duration(Duration.seconds(1))
                .fadeOut(answerPopUp)
                .buildAndPlay(this);
    }

    /**
     * Sets the provided plug as the currently selected one.
     *
     * @param player The player selecting a device. 1 or 2
     * @param colour The colour selected (0=Red, 1=Yellow, 2=Blue)
     */
    public void setPlug(int player, int colour) {
        if (player == 1) {
            getContentRoot().getChildren().removeAll(textureAnswerP1);
        } else if (player == 2) {
            getContentRoot().getChildren().removeAll(textureAnswerP2);
        }
        cleanPopUp();

        switch (colour) {
        case 0 -> setImagePlug("plug-red.png", player);
        case 1 -> setImagePlug("plug-yellow.png", player);
        case 2 -> setImagePlug("plug-blue.png", player);
        default -> System.out.println("unknown colour!");
        }

        if (player == 1) {
            getContentRoot().getChildren().addAll(textureAnswerP1);
            quizLogic.setAnswerP1(colour);
        } else if (player == 2) {
            getContentRoot().getChildren().addAll(textureAnswerP2);
            quizLogic.setAnswerP2(colour);
        }

    }

    private void cleanPopUp() {
        if (answerPopUp != null) {
            getContentRoot().getChildren().removeAll(answerPopUp);
            answerPopUp = null;
        }
    }

    private Text buildTextbox(String text, BoxType type) {
        Text label = UIHelper.getUITitle(text, new Point2D(type.x, type.y), 16);
        label.setWrappingWidth(type.width);
        label.setLineSpacing(8);

        return label;
    }

    private void clearQuiz() {
        getContentRoot().getChildren()
                .removeAll(currentQuiz[0], currentQuiz[1], currentQuiz[2], currentQuiz[3], textureAnswerP1,
                        textureAnswerP2);
        falseAnswerCount = 0;
    }

    private void nextQuestion() {
        quizLogic.nextQuestion();
        quizLogic.resetAnswers();

        if (quizLogic.quizDone()) {
            quizLogic.setDoorOpen(true);
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            currentQuiz = buildQuiz(quizLogic.getQuestion());
        }
    }

    private Node[] buildQuiz(QuizQuestion question) {
        Text questionHBox = buildTextbox(question.question(), BoxType.QUESTION);
        Text firstHBox = buildTextbox(question.answerOptions()[0], BoxType.REDANSWER);
        Text secondHBox = buildTextbox(question.answerOptions()[1], BoxType.YELLOWANSWER);
        Text thirdHBox = buildTextbox(question.answerOptions()[2], BoxType.BLUEANSWER);

        getContentRoot().getChildren().addAll(questionHBox, firstHBox, secondHBox, thirdHBox);
        return new Node[] {questionHBox, firstHBox, secondHBox, thirdHBox};
    }
}
