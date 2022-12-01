package ch.fhnw.strombewusst.ui.scene;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * This class defines the layout of our puzzle sub-scene. It gets rendered on top of the main menu when the
 * player gets to the main desk in the room button is pressed.
 */
public class PuzzleSubScene extends SubScene {

    public PuzzleSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/puzzlebackground.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Hier kommt Puzzle");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(getAppWidth());
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(50);

        Button btnBack = new Button("Back");
        btnBack.getStyleClass().add("main_menu_button");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> getSceneService().popSubScene());

        HBox backHBox = new HBox(btnBack);
        backHBox.setPrefWidth(getAppWidth());
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setTranslateY(getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleHBox, backHBox);
    }
}
