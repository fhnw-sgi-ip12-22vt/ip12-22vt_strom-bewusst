package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.HighScoreService;
import ch.fhnw.strombewusst.InputHandler;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGL.*;


/**
 * This class defines the layout of the main menu.
 */
public class MainMenu extends FXGLMenu {
    private final VBox leaderboardNames;
    private final VBox leaderboardScores;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Leaderboard");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setTranslateX(750);
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(100);

        Button btnPlay = new Button("Spiel starten");
        // fireNewGame() clears the Scene and calls initGame(), to spawn all entities.
        btnPlay.setOnAction(e -> fireNewGame());
        btnPlay.getStyleClass().add("main_menu_button");

        //Leaderboard
        leaderboardNames = new VBox();
        leaderboardNames.setAlignment(Pos.CENTER_LEFT);
        leaderboardScores = new VBox();
        leaderboardScores.setAlignment(Pos.CENTER_RIGHT);

        HBox leaderboardHBox = new HBox(100, leaderboardNames, leaderboardScores);
        leaderboardHBox.setAlignment(Pos.CENTER);
        leaderboardHBox.setTranslateX(770);
        leaderboardHBox.setTranslateY(200);

        VBox buttonVBox = new VBox(30, btnPlay);
        buttonVBox.setTranslateX(780);
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setTranslateY(500);

        getContentRoot().getChildren().addAll(bg, titleHBox, buttonVBox, leaderboardHBox);

        getInput().addAction(new UserAction("update team name first") {
            @Override
            protected void onActionBegin() {
                InputHandler.handlePlayerUp(null);
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("update team name second") {
            @Override
            protected void onActionBegin() {
                InputHandler.handlePlayerDown(null);
            }
        }, KeyCode.S);
    }

    @Override
    public void onCreate() {
        getSaveLoadService().readAndLoadTask(Config.SAVE_FILE_NAME)
                .onFailure(error -> Logger.get(MainMenu.class).warning("Cannot read " + Config.SAVE_FILE_NAME))
                .run();

        leaderboardNames.getChildren().clear();
        leaderboardScores.getChildren().clear();
        HighScoreService highScoreService = getService(HighScoreService.class);
        highScoreService.getHighScores().forEach(data -> {
            Label n = new Label(data.getTag());
            n.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardNames.getChildren().add(n);

            Label s = new Label(Integer.toString(data.getScore()));
            s.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardScores.getChildren().add(s);
        });
        if (highScoreService.getHighScores().isEmpty()) {
            Label n = new Label("noch keine High-Scores");
            n.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
            leaderboardNames.getChildren().add(n);
        }
    }
}
