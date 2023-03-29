package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;


public class PlayerDeskHandler extends CollisionHandler {
    private final HBox player1HBox = new HBox();
    private final HBox player2HBox = new HBox();
    private final List<String> infoBoxes = FXGL.getAssetLoader().loadText("room1_deskinfo.txt");

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
        player1HBox.setTranslateX(950);
        player1HBox.setTranslateY(25);
        FXGL.runOnce(() -> FXGL.getSceneService().getCurrentScene().addChild(player1HBox), Duration.ZERO);

        player2HBox.setTranslateX(950);
        player2HBox.setTranslateY(380);
        FXGL.runOnce(() -> FXGL.getSceneService().getCurrentScene().addChild(player2HBox), Duration.ZERO);
    }

    public PlayerDeskHandler(EntityType player, EntityType type) {
        super(player, type);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity desk) {
        int deskNum = desk.getComponent(DeskComponent.class).getDeskNum();
        Text title = new Text(infoBoxes.get(deskNum));
        title.setWrappingWidth(300);
        title.getStyleClass().add("message");

        if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
            player1HBox.getChildren().clear();
            player1HBox.getChildren().add(title);
        } else {
            player2HBox.getChildren().clear();
            player2HBox.getChildren().add(title);
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        try {
            if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
                player1HBox.getChildren().clear();
            } else {
                player2HBox.getChildren().clear();
            }
        } catch (IllegalArgumentException ignored) {
            // BUGFIX: prevent crash on edge-case when text is shown during level change
        }
    }
}
