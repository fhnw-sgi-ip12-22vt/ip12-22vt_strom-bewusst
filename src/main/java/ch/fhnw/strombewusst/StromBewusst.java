package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

public class StromBewusst extends GameApplication {
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Strom Bewusst");
        settings.setVersion("0.1_BETA");
        settings.getCSSList().add("main.css");

        settings.setMainMenuEnabled(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new StromBewusstFactory());

        player = FXGL.spawn("player", FXGL.getAppCenter());
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> player.translateY(-5));
        FXGL.onKey(KeyCode.A, () -> player.translateX(-5));
        FXGL.onKey(KeyCode.S, () -> player.translateY(5));
        FXGL.onKey(KeyCode.D, () -> player.translateX(5));
    }
}
