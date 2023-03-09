package ch.fhnw.strombewusst;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EndGameScene extends SubScene {
    private final Button btnEnd;

    EndGameScene() {
        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Spiel geschafft!");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(getAppWidth());
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(50);

        btnEnd = new Button("Back");
        btnEnd.getStyleClass().add("main_menu_button");
        btnEnd.setStyle("-fx-text-fill: black;");
        btnEnd.setOnAction(e -> getGameController().gotoMainMenu());
        HBox endHBox = new HBox(btnEnd);
        endHBox.setPrefWidth(getAppWidth());
        endHBox.setAlignment(Pos.CENTER);
        endHBox.setTranslateY(getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleHBox, endHBox);
    }
}

