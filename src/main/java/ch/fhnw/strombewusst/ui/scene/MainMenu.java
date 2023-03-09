package ch.fhnw.strombewusst.ui.scene;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

/**
 * This class defines the layout of the main menu.
 */
public class MainMenu extends FXGLMenu {
    public static void focusPreviousNode() {
        Platform.runLater(() -> {
            Scene s = getSceneService().getCurrentScene().getContentRoot().getScene();
            if (getSceneService().getCurrentScene() instanceof MainMenu) {
                Node node = s.focusOwnerProperty().get();

                KeyEvent event = new KeyEvent(
                        null,
                        null,
                        KeyEvent.KEY_PRESSED,
                        "",
                        "",
                        KeyCode.UP,
                        false,
                        false,
                        false,
                        false
                );

                node.fireEvent(event);
            }
        });
    }
    public static void focusNextNode() {
        Platform.runLater(() -> {
            Scene s = getSceneService().getCurrentScene().getContentRoot().getScene();
            if (getSceneService().getCurrentScene() instanceof MainMenu) {
                Node node = s.focusOwnerProperty().get();

                KeyEvent event = new KeyEvent(
                        null,
                        null,
                        KeyEvent.KEY_PRESSED,
                        "",
                        "",
                        KeyCode.DOWN,
                        false,
                        false,
                        false,
                        false
                );

                node.fireEvent(event);
            }
        });
    }

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
    }
}
