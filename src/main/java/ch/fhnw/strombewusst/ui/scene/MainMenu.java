package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Config;
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

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * This class defines the layout of the main menu.
 */
public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Strom Bewusst");
        title.getStyleClass().add("title");

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(getAppWidth());
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.setTranslateY(50);

        Button btnPlay = new Button("Play");
        // fireNewGame() clears the Scene and calls initGame(), to spawn all entities.
        btnPlay.setOnAction(e -> fireNewGame());
        btnPlay.getStyleClass().add("main_menu_button");

        Button btnLeaderboard = new Button("Leaderboard");
        btnLeaderboard.setOnAction(e -> getSceneService().pushSubScene(new LeaderboardSubScene()));
        btnLeaderboard.getStyleClass().add("main_menu_button");

        VBox buttonVBox = new VBox(30, btnPlay, btnLeaderboard);
        buttonVBox.setPrefWidth(getAppWidth());
        buttonVBox.setAlignment(Pos.TOP_CENTER);
        buttonVBox.setTranslateY(300);

        getContentRoot().getChildren().addAll(bg, titleHBox, buttonVBox);

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
    }
}
