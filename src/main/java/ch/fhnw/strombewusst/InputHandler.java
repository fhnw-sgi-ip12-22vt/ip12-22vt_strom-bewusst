package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.UIHelper;
import ch.fhnw.strombewusst.ui.scene.DeviceOrderSubScene;
import ch.fhnw.strombewusst.ui.scene.EndGameSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.QuizSubScene;
import com.almasb.fxgl.cutscene.CutsceneScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.Scene;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.List;

/**
 * Handles the inputs depending on the current game state.
 */
public class InputHandler {
    /**
     * Resets the inactivity timer
     */
    public static void logInteraction() {
        try {
            FXGL.<Timer>geto("timer").logInteraction();
        } catch (Exception ignored) {
            // Timer not yet initialized - ignore
        }
    }

    /**
     * Handles movement to the right
     *
     * @param player the player entity to apply the movement to
     */
    public static void handlePlayerRight(Entity player) {
        logInteraction();
        if (player != null) {
            player.getComponent(PlayerComponent.class).moveRight();
        }
    }

    /**
     * Handles movement to the left
     *
     * @param player the player entity to apply the movement to
     */
    public static void handlePlayerLeft(Entity player) {
        logInteraction();
        if (player != null) {
            player.getComponent(PlayerComponent.class).moveLeft();
        }
    }

    /**
     * Handles movement up
     *
     * @param player the player entity to apply the movement to
     */
    public static void handlePlayerUp(Entity player) {
        logInteraction();
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            UIHelper.focusPreviousNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveUp();
            }
        }
    }

    /**
     * Handles movement down
     *
     * @param player the player entity to apply the movement to
     */
    public static void handlePlayerDown(Entity player) {
        logInteraction();
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            UIHelper.focusNextNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveDown();
            }
        }
    }

    /**
     * Handles vertical idle
     *
     * @param player the player entity to vertically idle
     */
    public static void handlePlayerVerticalIdle(Entity player) {
        logInteraction();
        if (player != null) {
            player.getComponent(PlayerComponent.class).stopMovingY();
        }
    }

    /**
     * Handles horizontal idle
     *
     * @param player the player entity to horizontally idle
     */
    public static void handlePlayerHorizontalIdle(Entity player) {
        logInteraction();
        if (player != null) {
            player.getComponent(PlayerComponent.class).stopMovingX();
        }
    }

    /**
     * Handles presses of the left button
     *
     * @param player the player entity whose player has pressed the button
     */
    public static void handleButtonLeft(Entity player) {
        logInteraction();
        int playerNumber = player.getInt("playerNum");

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof QuizSubScene quizSubScene) {
            Platform.runLater(() -> quizSubScene.setPlug(playerNumber, 0));
        } else if (currentScene instanceof DeviceOrderSubScene deviceOrderSubScene) {
            Platform.runLater(() -> deviceOrderSubScene.setDevice(playerNumber, 0));
        }
    }

    /**
     * Handles presses of the middle button
     *
     * @param player the player entity whose player has pressed the button
     */
    public static void handleButtonMiddle(Entity player) {
        logInteraction();
        int playerNumber = player.getInt("playerNum");

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof QuizSubScene quizSubScene) {
            Platform.runLater(() -> quizSubScene.setPlug(playerNumber, 1));
        } else if (currentScene instanceof DeviceOrderSubScene deviceOrderSubScene) {
            Platform.runLater(() -> deviceOrderSubScene.setDevice(playerNumber, 1));
        }
    }

    /**
     * Handles presses of the right button
     *
     * @param player the player entity whose player has pressed the button
     */
    public static void handleButtonRight(Entity player) {
        logInteraction();
        int playerNumber = player.getInt("playerNum");

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof QuizSubScene quizSubScene) {
            Platform.runLater(() -> quizSubScene.setPlug(playerNumber, 2));
        } else if (currentScene instanceof DeviceOrderSubScene deviceOrderSubScene) {
            Platform.runLater(() -> deviceOrderSubScene.setDevice(playerNumber, 2));
        }
    }

    /**
     * Handles presses of the select (lower) button
     *
     * @param player the player entity whose player has pressed the button
     */
    public static void handleSelect(Entity player) {
        logInteraction();
        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof MainMenu) {
            UIHelper.confirmSelectedNode();
            return;
        } else if (currentScene instanceof EndGameSubScene endGameSubScene) {
            Platform.runLater(
                    () -> ((StromBewusst) FXGL.getApp()).saveAndReset(endGameSubScene.getTeamName().toString()));
            return;
        } else if (currentScene instanceof QuizSubScene quizSubScene) {
            Platform.runLater(quizSubScene::checkAnswers);
            return;
        } else if (currentScene instanceof DeviceOrderSubScene deviceOrderSubScene) {
            Platform.runLater(deviceOrderSubScene::checkAnswers);
            return;
        } else if (currentScene instanceof CutsceneScene) {
            currentScene.getInput().mockKeyPress(KeyCode.ENTER);
            currentScene.getInput().mockKeyRelease(KeyCode.ENTER);
            return;
        }

        if (player == null) {
            return;
        }

        QuizLogic quizLogic = FXGL.geto("quizLogic");
        DeviceOrderLogic deviceOrderLogic = FXGL.geto("deviceOrderLogic");

        // check collision with main desk and open puzzle subscene if so
        List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(EntityType.MAINDESK);
        for (Entity e : entities) {
            try {
                // player.isColliding(e) only works if they are intersecting, not if they're right next to each other
                // instead we check for the distance between bounding boxes
                if (e.distanceBBox(player) > 1) {
                    continue;
                }
                if (FXGL.geti("level") == 1 && !quizLogic.quizDone()) {
                    FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(
                            new QuizSubScene(quizLogic)), Duration.ZERO);
                    return;
                } else if (FXGL.geti("level") == 2 && !deviceOrderLogic.isDeviceOrderDone()) {
                    FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(
                            new DeviceOrderSubScene(deviceOrderLogic)), Duration.ZERO);
                    return;
                }
            } catch (NullPointerException ignored) {
                // BUGFIX: there is a weird race-condition that causes a null-pointer exception in rare cases.
                // if that happens, we just ignore it and continue
            }
        }

        // check collision with door and switch levels if so
        Entity door = FXGL.getGameWorld().getSingleton(EntityType.DOOR);

        if (door.distanceBBox(player) > 1) {
            return;
        }

        if (door.getBoolean("open")) {
            FXGL.runOnce(() -> FXGL.getGameScene().getViewport().fade(((StromBewusst) FXGL.getApp())::nextLevel),
                    Duration.ZERO);
        }
    }

    /**
     * Handles presses of the back (upper) button
     *
     * @param player the player entity whose player has pressed the button
     */
    public static void handleBack(Entity player) {
        logInteraction();
        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof QuizSubScene) {
            FXGL.<QuizLogic>geto("quizLogic").resetAnswers();
            Platform.runLater(() -> FXGL.getSceneService().popSubScene());
        } else if (currentScene instanceof DeviceOrderSubScene deviceOrderSubScene) {
            Platform.runLater(() -> {
                deviceOrderSubScene.resetAnswers();
                FXGL.getSceneService().popSubScene();
            });
        } else if (currentScene instanceof EndGameSubScene endGameSubScene) {
            if (player.getInt("playerNum") == 1) {
                endGameSubScene.getTeamName().updateFirst();
            } else {
                endGameSubScene.getTeamName().updateSecond();
            }
            Platform.runLater(endGameSubScene::updateTeamNameLabel);
        }
    }
}
