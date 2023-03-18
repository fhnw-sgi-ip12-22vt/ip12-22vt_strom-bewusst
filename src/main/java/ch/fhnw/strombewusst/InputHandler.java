package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class InputHandler {
    public static void handlePlayerRight(Entity player) {
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            MainMenu.focusNextNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveRight();
            }
        }
    }

    public static void handlePlayerLeft(Entity player) {
        if (FXGL.getSceneService().getCurrentScene() instanceof MainMenu) {
            MainMenu.focusPreviousNode();
        } else {
            if (player != null) {
                player.getComponent(PlayerComponent.class).moveLeft();
            }
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
}
