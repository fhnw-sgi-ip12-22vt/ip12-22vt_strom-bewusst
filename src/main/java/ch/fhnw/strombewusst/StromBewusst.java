package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.ControllerComponents.Ads1115;
import ch.fhnw.strombewusst.ControllerComponents.JoystickAnalog;
import ch.fhnw.strombewusst.collision.PlayerDoorHandler;
import ch.fhnw.strombewusst.collision.PlayerPlayerHandler;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import javafx.scene.input.KeyCode;
import java.util.Map;
import ch.fhnw.strombewusst.ControllerComponents.helpers.PIN;


/**
 * This is the main class of our game, the methods of this class initialize the game.
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;

    private Context pi4j;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Strom Bewusst");
        settings.setVersion("0.1_BETA");
        settings.getCSSList().add("main.css");

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);

        settings.setMainMenuEnabled(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    /**
     * This method initializes the game (after the "Play" Button on the Leaderboard is pressed).
     * It spawns the players and entities.
     */
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new StromBewusstFactory());

        spawn("wall", new SpawnData(20, 0).put("width", 0d).put("height", (double) getAppHeight()));
        spawn("wall", new SpawnData(0, 50).put("width", (double) getAppWidth()).put("height", 0d));
        spawn("wall", new SpawnData(890, 0).put("width", 0d).put("height", (double) getAppHeight()));
        spawn("wall", new SpawnData(0, getAppHeight() - 30).put("width", (double) getAppWidth()).put("height", 0d));

        spawn("emptyRoom");
        player1 = spawn("player", new SpawnData(566, 92).put("playerNum", 1));
        player2 = spawn("player", new SpawnData(694, 92).put("playerNum", 2));

        FXGL.spawn("main-desk", 264, 75);
        FXGL.spawn("door", 618, 6);

        FXGL.spawn("desk", new SpawnData(103, 267).put("deskNum", 0));
        FXGL.spawn("desk", new SpawnData(264, 267).put("deskNum", 1));
        FXGL.spawn("desk", new SpawnData(425, 267).put("deskNum", 2));
        FXGL.spawn("desk", new SpawnData(586, 267).put("deskNum", 3));
        FXGL.spawn("desk", new SpawnData(747, 267).put("deskNum", 4));

        FXGL.spawn("desk", new SpawnData(103, 405).put("deskNum", 5));
        FXGL.spawn("desk", new SpawnData(264, 405).put("deskNum", 6));
        FXGL.spawn("desk", new SpawnData(425, 405).put("deskNum", 7));
        FXGL.spawn("desk", new SpawnData(586, 405).put("deskNum", 8));
        FXGL.spawn("desk", new SpawnData(747, 405).put("deskNum", 9));

        FXGL.spawn("desk", new SpawnData(103, 543).put("deskNum", 10));
        FXGL.spawn("desk", new SpawnData(264, 543).put("deskNum", 11));
        FXGL.spawn("desk", new SpawnData(425, 543).put("deskNum", 12));
        FXGL.spawn("desk", new SpawnData(586, 543).put("deskNum", 13));
        FXGL.spawn("desk", new SpawnData(747, 543).put("deskNum", 14));
    }

    /**
     * This method initializes the input. It gets executed *before* initGame.
     */
    @Override
    protected void initInput() {
        // player1 Movement
        pi4j = Pi4J.newAutoContext();
        Ads1115 ads1115 = new Ads1115(pi4j, 0x01, Ads1115.GAIN.GAIN_4_096V, Ads1115.ADDRESS.GND, 4);
        JoystickAnalog controller1 = new JoystickAnalog(pi4j, ads1115, 0, 1, 3.3, true, PIN.D6);

        getInput().addAction(new UserAction("player1 Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.H);
        getInput().addAction(new UserAction("player1 Left") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("player1 Up") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveUp();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("player1 Down") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveDown();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.S);

        // player2 Movement
        getInput().addAction(new UserAction("player2 Right") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.L);
        getInput().addAction(new UserAction("player2 Left") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.J);
        getInput().addAction(new UserAction("player2 Up") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveUp();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.I);
        getInput().addAction(new UserAction("player2 Down") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveDown();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.K);
    }


    /**
     * This method initializes the physics engine that is used to handle collisions.
     */
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        physicsWorld.addCollisionHandler(new PlayerPlayerHandler());
        physicsWorld.addCollisionHandler(new PlayerDoorHandler());

        physicsWorld.addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.DESK) {
            Entity message1, message2;

            @Override
            protected void onCollisionBegin(Entity player, Entity desk) {
                if (player == player1) {
                    message1 = spawn("message", new SpawnData(920, 20).put("msgNum", desk.getComponent(DeskComponent.class).getDeskNum()));
                } else {
                    message2 = spawn("message", new SpawnData(920, 360).put("msgNum", desk.getComponent(DeskComponent.class).getDeskNum()));
                }

            }

            @Override
            protected void onCollisionEnd(Entity player, Entity desk) {
                if (player == player1) {
                    message1.removeFromWorld();
                } else {
                    message2.removeFromWorld();
                }
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("level", 0);
        vars.put("bullets", 999);
        vars.put("laser", 50);
        vars.put("rockets", 10);
        vars.put("heat", 0);
        vars.put("overheating", false);
        vars.put("shield", 0);
        vars.put("hasShield", false);
    }
}

