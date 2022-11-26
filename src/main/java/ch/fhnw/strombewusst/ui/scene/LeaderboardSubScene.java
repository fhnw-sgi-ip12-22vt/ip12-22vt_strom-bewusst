package ch.fhnw.strombewusst.ui.scene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LeaderboardSubScene extends SubScene {
    LeaderboardSubScene() {
        Texture bg = FXGL.getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(FXGL.getAppWidth());
        bg.setFitHeight(FXGL.getAppHeight());

        Label title = new Label("Leaderboard");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(FXGL.getAppWidth());
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(50);

        VBox leaderboardNames = new VBox();
        leaderboardNames.setAlignment(Pos.CENTER_LEFT);
        VBox leaderboardScores = new VBox();
        leaderboardScores.setAlignment(Pos.CENTER_RIGHT);

        HBox leaderboardHBox = new HBox(100, leaderboardNames, leaderboardScores);
        leaderboardHBox.setAlignment(Pos.CENTER);
        leaderboardHBox.setPrefWidth(FXGL.getAppWidth());
        leaderboardHBox.setTranslateY(200);

        for (int i = 10; i > 0; i--) {
            Label n = new Label("Player " + i);
            n.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardNames.getChildren().add(n);

            Label s = new Label("" + (i * 1000));
            s.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardScores.getChildren().add(s);
        }

        Button btnBack = new Button("Back");
        btnBack.getStyleClass().add("main_menu_button");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> FXGL.getSceneService().popSubScene());

        HBox backHBox = new HBox(btnBack);
        backHBox.setPrefWidth(FXGL.getAppWidth());
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setTranslateY(FXGL.getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleHBox, leaderboardHBox, backHBox);
    }
}
