package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.scene.DeviceOrderSubScene;
import ch.fhnw.strombewusst.ui.scene.EndGameSubScene;
import ch.fhnw.strombewusst.ui.scene.LeaderboardSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.NodeSelectionHelper;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.Scene;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.List;


public class InputHandler {
    public static void handlePlayerRight(Entity player) {
        if (player != null) {
            player.getComponent(PlayerComponent.class).moveRight();
        }
    }

    public static void handlePlayerLeft(Entity player) {
        if (player != null) {
            player.getComponent(PlayerComponent.class).moveLeft();
        }
    }

    public static void handlePlayerUp(Entity player) {
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            NodeSelectionHelper.focusPreviousNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveUp();
            }
        }
    }

    public static void handlePlayerDown(Entity player) {
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            NodeSelectionHelper.focusNextNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveDown();
            }
        }
    }

    public static void handlePlayerVerticalIdle(Entity player) {
        if (player != null) {
            player.getComponent(PlayerComponent.class).stopMovingY();
        }
    }

    public static void handlePlayerHorizontalIdle(Entity player) {
        if (player != null) {
            player.getComponent(PlayerComponent.class).stopMovingX();
        }
    }

    public static void handleButtonLeft(Entity player) {
        int playerNumber = player.getComponent(PlayerComponent.class).getPlayerNum();

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof PuzzleSubScene) {
            Platform.runLater(() -> ((PuzzleSubScene) currentScene).setPlug(playerNumber, 0));
        } else if (currentScene instanceof DeviceOrderSubScene) {
            Platform.runLater(() -> ((DeviceOrderSubScene) currentScene).setDevice(playerNumber, 0));
        }
    }

    public static void handleButtonMiddle(Entity player) {
        int playerNumber = player.getComponent(PlayerComponent.class).getPlayerNum();

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof PuzzleSubScene) {
            Platform.runLater(() -> ((PuzzleSubScene) currentScene).setPlug(playerNumber, 1));
        } else if (currentScene instanceof DeviceOrderSubScene) {
            Platform.runLater(() -> ((DeviceOrderSubScene) currentScene).setDevice(playerNumber, 1));
        }
    }

    public static void handleButtonRight(Entity player) {
        int playerNumber = player.getComponent(PlayerComponent.class).getPlayerNum();

        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof PuzzleSubScene) {
            Platform.runLater(() -> ((PuzzleSubScene) currentScene).setPlug(playerNumber, 2));
        } else if (currentScene instanceof DeviceOrderSubScene) {
            Platform.runLater(() -> ((DeviceOrderSubScene) currentScene).setDevice(playerNumber, 2));
        }
    }

    public static void handleSelect(Entity player) {
        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof MainMenu
                || currentScene instanceof LeaderboardSubScene
                || currentScene instanceof EndGameSubScene) {
            NodeSelectionHelper.confirmSelectedNode();
            return;
        } else if (currentScene instanceof PuzzleSubScene) {
            Platform.runLater(() -> ((PuzzleSubScene) currentScene).checkAnswers());
            return;
        } else if (currentScene instanceof DeviceOrderSubScene) {
            Platform.runLater(() -> ((DeviceOrderSubScene) currentScene).checkAnswers());
            return;
        }

        if (player == null) {
            return;
        }

        // check collision with main desk and open puzzle subscene if so
        List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(EntityType.MAINDESK);
        for (Entity e : entities) {
            try {
                // player.isColliding(e) only works if they are intersecting, not if they're right next to each other
                // instead we check for the distance between bounding boxes
                if (e.distanceBBox(player) <= 1 && ((StromBewusst) FXGL.getApp()).getLevel() == 1
                        && !FXGL.<QuizLogic>geto("quizLogic").quizDone()) {
                    FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new PuzzleSubScene()), Duration.ZERO);
                    return;
                } else if (e.distanceBBox(player) <= 1 && ((StromBewusst) FXGL.getApp()).getLevel() == 2
                        && !FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").deviceOrderDone()) {
                    FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new DeviceOrderSubScene()), Duration.ZERO);
                    return;
                }
            } catch (NullPointerException ignored) {
                // BUGFIX: there is a weird race-condition that causes a null-pointer exception in rare cases.
                // if that happens, we just ignore it and continue
            }
        }

        // check collision with door and switch levels if so
        entities = FXGL.getGameWorld().getEntitiesByType(EntityType.DOOR);
        for (Entity e : entities) {
            if (!player.isColliding(e)) {
                continue;
            }
            if (((StromBewusst) FXGL.getApp()).getLevel() == 1
                    && FXGL.<QuizLogic>geto("quizLogic").isDoorOpen()) {
                FXGL.runOnce(
                        () -> FXGL.getGameScene().getViewport().fade(() -> ((StromBewusst) FXGL.getApp()).nextLevel()),
                        Duration.ZERO);
                return;
            } else if (((StromBewusst) FXGL.getApp()).getLevel() == 2
                    && FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").isDoorOpen()) {
                FXGL.runOnce(
                        () -> FXGL.getGameScene().getViewport().fade(() -> ((StromBewusst) FXGL.getApp()).nextLevel()),
                        Duration.ZERO);
            }
        }
    }

    public static void handleBack(Entity player) {
        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof PuzzleSubScene) {
            FXGL.<QuizLogic>geto("quizLogic").resetAnswers();
            Platform.runLater(() -> FXGL.getSceneService().popSubScene());
        } else if (currentScene instanceof DeviceOrderSubScene) {
            Platform.runLater(() -> {
                ((DeviceOrderSubScene) currentScene).resetAnswers();
                FXGL.getSceneService().popSubScene();
            });
        }
    }
}
