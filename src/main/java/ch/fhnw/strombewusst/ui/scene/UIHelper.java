package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.Timer;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class UIHelper {
    /**
     * Focuses the previous node, by simulating an Arrow-Up KeyEvent.
     */
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

    /**
     * Focuses the next node, by simulating an Arrow-Down KeyEvent.
     */
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

    /**
     * Fires the action registered on the currently selected node.
     * This is akin to pressing the enter key while a node is selected.
     */
    public static void confirmSelectedNode() {
        Platform.runLater(() -> {
            Scene s = getSceneService().getCurrentScene().getContentRoot().getScene();
            Node node = s.focusOwnerProperty().get();

            node.fireEvent(new ActionEvent());
        });
    }

    /**
     * Starts a cutscene and overrides the Enter KeyView with a button texture.
     * @param cutscene the cutscene to be started
     */
    public static void showCutsceneWithButton(Cutscene cutscene) {
        FXGL.runOnce(() -> {
            FXGL.<Timer>geto("timer").pause();
            FXGL.getCutsceneService().startCutscene(cutscene, () -> FXGL.<Timer>geto("timer").unpause());

            Texture backButton = FXGL.getAssetLoader().loadTexture("red-button-icon-single.png", 68, 68);
            backButton.setTranslateX(Config.WIDTH - backButton.getWidth() - 10);
            backButton.setTranslateY(Config.HEIGHT - backButton.getHeight() - 10);

            // This code is very sketchy, since it depends on the implementation of the CutsceneScene in FXGL.
            // It may break after an FXGL update.
            Pane pane = (Pane) FXGL.getSceneService().getCurrentScene().getRoot().getChildren().get(0);
            backButton.opacityProperty().bind(pane.getChildren().get(3).opacityProperty());
            // remove the KeyView Node and add the new texture instead
            pane.getChildren().remove(4);
            pane.getChildren().add(backButton);
        }, Duration.ZERO);
    }

    /**
     * Creates a text label containing the time in timer with the required styling.
     *
     * @param timer the Timer from which the label gets generated
     * @param x x coordinate of the HBox containing the label
     * @param y y coordinate of the HBox containing the label
     * @return the generated HBox
     */
    public static HBox createTimerLabel(Timer timer, int x, int y) {
        Text timerLabel = new Text();
        timerLabel.textProperty().bind(timer.getTimerProperty());
        timerLabel.setStyle("-fx-font-size: 44px;");

        HBox timerHBox = new HBox(timerLabel);
        timerHBox.setTranslateX(x);
        timerHBox.setTranslateY(y);

        return timerHBox;
    }

    /**
     * Generates a text label containing the score with the required styling.
     *
     * @param score the Score object from which the label gets generated
     * @param x x coordinate of the HBox containing the label
     * @param y y coordinate of the HBox containing the label
     * @return the generated HBox
     */
    public static HBox createScoreLabel(Score score, int x, int y) {
        Text scoreLabel = new Text();
        scoreLabel.textProperty().bind(score.getScoreProperty().asString());
        scoreLabel.setStyle("-fx-font-size: 44px;");

        HBox scoreHBox = new HBox(scoreLabel);
        scoreHBox.setTranslateX(x);
        scoreHBox.setTranslateY(y);

        return scoreHBox;
    }
}
