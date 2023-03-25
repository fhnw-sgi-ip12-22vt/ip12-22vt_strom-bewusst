package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.scene.EndGameSubScene;
import ch.fhnw.strombewusst.ui.scene.LeaderboardSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.Scene;
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
            MainMenu.focusPreviousNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveUp();
            }
        }
    }

    public static void handlePlayerDown(Entity player) {
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            MainMenu.focusNextNode();
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

    public static void handleSelect(Entity player) {
        Scene currentScene = FXGL.getSceneService().getCurrentScene();
        if (currentScene instanceof MainMenu
                || currentScene instanceof LeaderboardSubScene
                || currentScene instanceof EndGameSubScene
                || currentScene instanceof PuzzleSubScene) {
            MainMenu.confirmSelectedNode();
            return;
        }

        if (player == null) {
            return;
        }

        // check collision with main desk and open puzzle subscene if so
        List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(EntityType.MAINDESK);
        for (Entity e : entities) {
            // player.isColliding(e) only works if they are intersecting, not if they're right next to each other
            // instead we check for the distance between bounding boxes
            if (e.distanceBBox(player) <= 1) {
                System.out.println("wonderful");
                FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new PuzzleSubScene()), Duration.ZERO);
                return;
            }
        }

        // check collision with door and switch levels if so
        entities = FXGL.getGameWorld().getEntitiesByType(EntityType.DOOR);
        for (Entity e : entities) {
            if (player.isColliding(e)) {
                FXGL.runOnce(
                        () -> FXGL.getGameScene().getViewport().fade(() -> ((StromBewusst) FXGL.getApp()).nextLevel()),
                        Duration.ZERO);
                return;
            }
        }
    }
}
