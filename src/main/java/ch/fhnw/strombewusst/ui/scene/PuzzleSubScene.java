package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getInput;

import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.HashMap;

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
    private Questbucket questBucket;

    int quizNum;
    HBox[] currentQuiz;
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

        getContentRoot().getChildren().addAll(bg,backHBox,inputsHBox);
        questBucket = new Questbucket();
        quizNum = 0;
        currentQuiz = buildQuiz(quizNum);
        inputs();

    }

    void inputs(){

        getInput().addAction(new UserAction("Green1 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT5);

        getInput().addAction(new UserAction("Red1 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT4);

        getInput().addAction(new UserAction("Blue1 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT6);

        getInput().addAction(new UserAction("Green2 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT8);

        getInput().addAction(new UserAction("Red2 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT7);

        getInput().addAction(new UserAction("Blue2 Button") {
            @Override
            protected void onActionBegin() {
                nextQuiz();
            }
        }, KeyCode.DIGIT9);
    }

    HBox buildTextbox(int quest,int num){
        Text box = new Text(questBucket.quest[quest][num]);
        box.getStyleClass().add("message");
        HBox boxHBox = new HBox(box);
        if(num == 0){
            boxHBox.setTranslateX(mainBoxX);
            boxHBox.setTranslateY(mainBoxY);
        }
        else if(num == 1){
            boxHBox.setTranslateX(firstBoxX);
            boxHBox.setTranslateY(firstBoxY);
        }
        else if(num == 2){
            boxHBox.setTranslateX(secondBoxX);
            boxHBox.setTranslateY(secondBoxY);
        }
        else{
            boxHBox.setTranslateX(thirdBoxX);
            boxHBox.setTranslateY(thirdBoxY);
        }
        return boxHBox;
    }

    void clearQuiz(){
        getContentRoot().getChildren().removeAll(currentQuiz[0],currentQuiz[1],currentQuiz[2],currentQuiz[3]);
    }

    void nextQuiz(){
        if(quizNum>questBucket.quest.length-2){
            getSceneService().popSubScene();
        }
        else{
            clearQuiz();
            quizNum++;
            currentQuiz = buildQuiz(quizNum);
        }
    }

    HBox[] buildQuiz(int i){
        HBox questionHBox = buildTextbox(i,0);
        HBox firstHBox = buildTextbox(i,1);
        HBox secondHBox = buildTextbox(i,2);
        HBox thirdHBox = buildTextbox(i,3);
        getContentRoot().getChildren().addAll(questionHBox, firstHBox, secondHBox, thirdHBox);
        HBox[] result = {questionHBox,firstHBox,secondHBox,thirdHBox};
        return result;
    }

}
class Questbucket {
    private String questionString1 = "Welche Option entspricht der Einheit Watt ?";
    private String firstString1 =  "(Newton * Meter) / Sekunde";
    private String secondString1 = "(Sekunde * Meter) / Newton";
    private String thirdString1 = "(Newton * Sekunde) / Meter";
    private String answer1 = "RED";

    private String questionString2 = "Was verbraucht am meisten Strom von diesen 4 Haushaltsgeräten? ";
    private String firstString2 =  "Wasserkocher";
    private String secondString2 = "Haarföhn";
    private String thirdString2 = "Handy laden";
    private String answer2 = "RED";

    private String questionString3 = "Warum ist der Stromverbrauch in einer Wohnung im Mehrfamilienhaus  niedriger als im Einfamilienhaus?";
    private String firstString3 =  "Weil in Mehrfamilienhäuser die Dusche geteilt wird.";
    private String secondString3 = "Weil die Nebenkosten bei Mehrfamilienhäuser auf alle Mieter verteilt werden.";
    private String thirdString3 = "Weil Einfamilienhäuser im Durchschnitt mehr Haushältgeräte besitzen.";
    private String answer3 = "GREEN";
    private String questionString4 = "Wann in der Woche ist der Strom am günstigsten?";
    private String firstString4 =  "Freitag um 19:00";
    private String secondString4 = "Samstag um 12:00";
    private String thirdString4 = "Es gibt günstigere Tage";
    private String answer4 = "BLUE";

    private String questionString5 = "Wieviel kostet der Strom in einem Jahr bei einer Familie, die 3’500kwH in diesem Jahr verbraucht hat?";
    private String firstString5 =  "735 Franken";
    private String secondString5 = "835 Franken";
    private String thirdString5 = "635 Franken";
    private String answer5 = "RED";

    private String questionString6 = "Geschirrspülmaschine oder von Hand spülen: Was spart mehr Strom?";
    private String firstString6 =  "Geschirrspülmaschine";
    private String secondString6 = "Von Hand spülen";
    private String thirdString6 = "Beide verbrauchen gleichviel Strom";
    private String answer6 = "RED";
    public String[][] quest = {{questionString1,firstString1,secondString1,thirdString1,answer1},{questionString2,firstString2,secondString2,thirdString2,answer2},{questionString3,firstString3,secondString3,thirdString3,answer3},{questionString4,firstString4,secondString4,thirdString4,answer4},{questionString5,firstString5,secondString5,thirdString5,answer5},{questionString6,firstString6,secondString6,thirdString6,answer6}};
}


/*
Text title = new Text("TEST");
        title.getStyleClass().add("message");
        HBox messageHBox = new HBox(title);
        messageHBox.setTranslateX(thirdBoxX);
        messageHBox.setTranslateY(thirdBoxY);
 */
        /*Button btnBack = new Button("CLICK");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> getSceneService().popSubScene());

        HBox backHBox = new HBox(btnBack);
        backHBox.setPrefWidth(getAppWidth());
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setTranslateY(getAppHeight() - 140);*/