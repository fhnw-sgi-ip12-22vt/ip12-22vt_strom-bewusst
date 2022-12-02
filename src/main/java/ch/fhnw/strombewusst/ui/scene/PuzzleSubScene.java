package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public PuzzleSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        getContentRoot().getChildren().addAll(bg);
        buildQuiz();
    }

    void buildQuiz(){
        String questionString = "Welche Option entspricht der Einheit Watt ?";
        String firstString =  "(Newton * Meter) / Sekunde";
        String secondString = "(Sekunde * Meter) / Newton";
        String thirdString = "(Newton * Sekunde) / Meter";

        Text question = new Text(questionString);
        question.getStyleClass().add("message");
        HBox questionHBox = new HBox(question);
        questionHBox.setTranslateX(mainBoxX);
        questionHBox.setTranslateY(mainBoxY);

        Text first = new Text(firstString);
        first.getStyleClass().add("message");
        HBox firstHBox = new HBox(first);
        firstHBox.setTranslateX(firstBoxX);
        firstHBox.setTranslateY(firstBoxY);

        Text second = new Text(secondString);
        second.getStyleClass().add("message");
        HBox secondHBox = new HBox(second);
        secondHBox.setTranslateX(secondBoxX);
        secondHBox.setTranslateY(secondBoxY);

        Text third = new Text(thirdString);
        third.getStyleClass().add("message");
        HBox thirdHBox = new HBox(third);
        thirdHBox.setTranslateX(thirdBoxX);
        thirdHBox.setTranslateY(thirdBoxY);

        getContentRoot().getChildren().addAll(questionHBox,firstHBox,secondHBox,thirdHBox);
    }

}
class Textbucket {
    public String questionString1 = "Welche Option entspricht der Einheit Watt ?";
    public String firstString1 =  "(Newton * Meter) / Sekunde";
    public String secondString1 = "(Sekunde * Meter) / Newton";
    public String thirdString1 = "(Newton * Sekunde) / Meter";

    public String questionString2 = "Welche Option entspricht der Einheit Watt ?";
    public String firstString2 =  "(Newton * Meter) / Sekunde";
    public String secondString2 = "(Sekunde * Meter) / Newton";
    public String thirdString2 = "(Newton * Sekunde) / Meter";

    public String questionString3 = "Welche Option entspricht der Einheit Watt ?";
    public String firstString3 =  "(Newton * Meter) / Sekunde";
    public String secondString3 = "(Sekunde * Meter) / Newton";
    public String thirdString3 = "(Newton * Sekunde) / Meter";

    public String questionString4 = "Welche Option entspricht der Einheit Watt ?";
    public String firstString4 =  "(Newton * Meter) / Sekunde";
    public String secondString4 = "(Sekunde * Meter) / Newton";
    public String thirdString4 = "(Newton * Sekunde) / Meter";

    public String questionString5 = "Welche Option entspricht der Einheit Watt ?";
    public String firstString5 =  "(Newton * Meter) / Sekunde";
    public String secondString5 = "(Sekunde * Meter) / Newton";
    public String thirdString5 = "(Newton * Sekunde) / Meter";

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