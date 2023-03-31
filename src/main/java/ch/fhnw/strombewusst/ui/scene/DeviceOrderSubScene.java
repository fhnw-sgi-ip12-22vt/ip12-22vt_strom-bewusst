package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

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
 * This class defines the layout of our device order sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button 1 is pressed.
 */
public class DeviceOrderSubScene extends SubScene {
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

        String inputs = "Device Order";
        Text playerInputs = new Text(inputs);
        playerInputs.getStyleClass().add("message");
        HBox inputsHBox = new HBox(playerInputs);
        inputsHBox.setTranslateX(950);
        inputsHBox.setTranslateY(410);

        HBox steering = getTextBox("Device Order",950,380);
        HBox response = getTextBox("Device Order",950,210);
        HBox answerOne = getTextBox("Device Order", 955,555);
        HBox answerTwo = getTextBox("Device Order", 1135,555);

        getContentRoot().getChildren().addAll(bg, backHBox, inputsHBox,steering,response,answerOne,answerTwo);
    }

    HBox getTextBox(String txt, int x, int y){
        Text text = new Text(txt);
        text.getStyleClass().add("small_title");
        HBox textHBox = new HBox(text);
        textHBox.setTranslateX(x);
        textHBox.setTranslateY(y);
        return textHBox;
    }
}

