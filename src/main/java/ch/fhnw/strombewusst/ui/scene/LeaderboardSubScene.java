package ch.fhnw.strombewusst.ui.scene;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * This class defines the layout of our leaderboard sub-scene. It gets rendered on top of the main menu when the
 * "Leaderboard" button is pressed.
 */
public class LeaderboardSubScene extends SubScene {
    Button btnBack;

    LeaderboardSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Leaderboard");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(getAppWidth());
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(50);

        VBox leaderboardNames = new VBox();
        leaderboardNames.setAlignment(Pos.CENTER_LEFT);
        VBox leaderboardScores = new VBox();
        leaderboardScores.setAlignment(Pos.CENTER_RIGHT);

        HBox leaderboardHBox = new HBox(100, leaderboardNames, leaderboardScores);
        leaderboardHBox.setAlignment(Pos.CENTER);
        leaderboardHBox.setPrefWidth(getAppWidth());
        leaderboardHBox.setTranslateY(200);

        for (int i = 10; i > 0; i--) {
            Label n = new Label("Player " + i);
            n.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardNames.getChildren().add(n);

            Label s = new Label("" + (i * 1000));
            s.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardScores.getChildren().add(s);
        }

        btnBack = new Button("Back");
        btnBack.getStyleClass().add("main_menu_button");
        btnBack.setStyle("-fx-text-fill: black;");
        btnBack.setOnAction(e -> getSceneService().popSubScene());

        HBox backHBox = new HBox(btnBack);
        backHBox.setPrefWidth(getAppWidth());
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setTranslateY(getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleHBox, leaderboardHBox, backHBox);
    }

    @Override
    public void onUpdate(double tpf) {
        if (!(btnBack.isFocused())) {
            btnBack.requestFocus();
        }
    }
}
