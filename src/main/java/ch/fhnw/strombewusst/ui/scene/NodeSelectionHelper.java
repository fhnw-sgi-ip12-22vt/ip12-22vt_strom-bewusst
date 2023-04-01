package ch.fhnw.strombewusst.ui.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class NodeSelectionHelper {
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
    public static void confirmSelectedNode() {
        Platform.runLater(() -> {
            Scene s = getSceneService().getCurrentScene().getContentRoot().getScene();
            Node node = s.focusOwnerProperty().get();

            node.fireEvent(new ActionEvent());
        });
    }
}
