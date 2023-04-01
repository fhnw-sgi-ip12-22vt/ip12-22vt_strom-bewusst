package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.QuizQuestion;
import ch.fhnw.strombewusst.StromBewusst;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class defines the layout of our device sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class DeviceOrderSubScene extends SubScene {
    enum BoxType {
        STERRING(950,380,0),
        RESPONSE(950,210,0);
        final int x, y, width;

        BoxType(int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.width = width;
        }
    }

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

        HBox steering = getTextBox("Steuerung",BoxType.STERRING.x, BoxType.STERRING.y);
        HBox response = getTextBox("Rückmeldung",BoxType.RESPONSE.x,BoxType.RESPONSE.y);


        getContentRoot().getChildren().addAll(bg, backHBox,steering,response);

        //currentQuiz = buildQuiz(StromBewusst.QUIZ.getQuestion());
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

    void setImagePlug(String image, int player){
        //TODO
    }

    void inputs() {
        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                //TODO
                /*getContentRoot().getChildren().removeAll(textureAnswerP1);
                cleanPopUp();
                setImagePlug("plug-red.png",1);
                getContentRoot().getChildren().addAll(textureAnswerP1);
                StromBewusst.QUIZ.setAnswerP1(0);*/
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Green1 Button") {
            //TODO
        }, KeyCode.DIGIT5);


        getInput().addAction(new UserAction("Blue1 Button") {
           //TODO
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Red2 Button") {
            //TODO
        }, KeyCode.DIGIT7);


        getInput().addAction(new UserAction("Green2 Button") {
            //TODO
        }, KeyCode.DIGIT8);


        getInput().addAction(new UserAction("Blue2 Button") {
            //TODO
        }, KeyCode.DIGIT9);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {
                //TODO
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
        }, KeyCode.Q);
    }

    void cleanPopUp(){
        //TODO
        /*if(answerPopUp!=null){
            getContentRoot().getChildren().removeAll(answerPopUp);
            answerPopUp = null;
        }*/
    }

    private HBox buildTextbox(String text, BoxType type) {
        //TODO
        /*Text box = new Text(text);
        box.setWrappingWidth(type.width);
        box.getStyleClass().add("small_title");
        HBox hBox = new HBox(box);
        hBox.setTranslateX(type.x);
        hBox.setTranslateY(type.y);

        return hBox;*/
        return null;
    }

    void clearQuiz() {
        //TODO
        /*getContentRoot().getChildren()
            .removeAll(currentQuiz[0], currentQuiz[1], currentQuiz[2], currentQuiz[3], textureAnswerP1, textureAnswerP2, scoretable);
        falseAnswer = 0;*/
    }

    void nextQuestion() {
        //TODO
        /*StromBewusst.QUIZ.nextQuestion();

        if (StromBewusst.QUIZ.quizDone()) {
            StromBewusst.QUIZ.unlockDoor();
            getSceneService().popSubScene();
        } else {
            clearQuiz();
            currentQuiz = buildQuiz(StromBewusst.QUIZ.getQuestion());
        }*/
    }

    private HBox[] buildQuiz(QuizQuestion question) {
        //TODO
        /*HBox questionHBox = buildTextbox(question.question(), BoxType.QUESTION);
        HBox firstHBox = buildTextbox(question.answerOptions()[0], BoxType.REDANSWER);
        HBox secondHBox = buildTextbox(question.answerOptions()[1], BoxType.GREENANSWER);
        HBox thirdHBox = buildTextbox(question.answerOptions()[2], BoxType.BLUEANSWER);

        scoretable = StromBewusst.SCORE.pushScore(950,30);

        getContentRoot().getChildren().addAll(questionHBox, firstHBox, secondHBox, thirdHBox,scoretable);
        return new HBox[] {questionHBox, firstHBox, secondHBox, thirdHBox};*/
        return null;
    }

}
