package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerMainDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerPlayerHandler;
import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.input.Buttons;
import ch.fhnw.strombewusst.input.Controller;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.rooms.Room;
import ch.fhnw.strombewusst.rooms.Room1;
import ch.fhnw.strombewusst.rooms.Room2;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;

/**
 * This is the main class of our game, the methods of this class initialize the game.
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;
    private Entity door;
    private int level = 0;

    private Buttons p1Buttons;

    private Room[] rooms;

    private Controller p1Controller;
    private Controller p2Controller;

    public static void main(String[] args) {
        launch(args);
    }

    private void nextLevel() {
        level++;
        if (level <= rooms.length) {
            Room room = rooms[level - 1];

            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
            door = room.getDoor();

            getGameWorld().setLevel(room.getLevel());
        } else {
            level = 0;
            getGameController().gotoMainMenu();
            getSceneService().pushSubScene(new EndGameScene());
        }
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Strom Bewusst");
        settings.setVersion("0.1_BETA");
        settings.getCSSList().add("main.css");

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);

        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(false);
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
        rooms = new Room[] {new Room1(), new Room2()};
        level = 0;
        nextLevel();
    }

    /**
     * This method initializes the input. It gets executed *before* initGame.
     */
    @Override
    protected void initInput() {
        // Initializing the controllers, their input gets processed and the appropriate keypresses get "simulated"
        // By simulating the keypresses, the programm can be worked on locally by using WASD, but can be controlled
        // in production with the controller.
        try {
            p1Controller = new Controller(0, 1, PIN.D17);
            p2Controller = new Controller(2, 3, PIN.D6);

            p1Controller.onJoystickRight(() -> getInput().mockKeyPress(KeyCode.D));
            p1Controller.onJoystickLeft(() -> getInput().mockKeyPress(KeyCode.A));
            p1Controller.onJoystickHorizontalIdle(() -> {
                getInput().mockKeyRelease(KeyCode.A);
                getInput().mockKeyRelease(KeyCode.D);
            });

            p1Controller.onJoystickUp(() -> getInput().mockKeyPress(KeyCode.W));
            // p1Controller.onJoystickUp(MainMenu::focusPreviousNode);
            p1Controller.onJoystickDown(() -> getInput().mockKeyPress(KeyCode.S));
            // p1Controller.onJoystickDown(MainMenu::focusNextNode);
            p1Controller.onJoystickVerticalIdle(() -> {
                getInput().mockKeyRelease(KeyCode.W);
                getInput().mockKeyRelease(KeyCode.S);
            });

            p2Controller.onJoystickRight(() -> getInput().mockKeyPress(KeyCode.L));
            p2Controller.onJoystickLeft(() -> getInput().mockKeyPress(KeyCode.J));
            p2Controller.onJoystickHorizontalIdle(() -> {
                getInput().mockKeyRelease(KeyCode.L);
                getInput().mockKeyRelease(KeyCode.J);
            });

            p2Controller.onJoystickUp(() -> getInput().mockKeyPress(KeyCode.I));
            p2Controller.onJoystickDown(() -> getInput().mockKeyPress(KeyCode.K));
            p2Controller.onJoystickVerticalIdle(() -> {
                getInput().mockKeyRelease(KeyCode.I);
                getInput().mockKeyRelease(KeyCode.K);
            });
        } catch (Exception ignored) {
            System.out.println("failed to initialize controller, proceeding");
        }

        // player1 Movement
        getInput().addAction(new UserAction("player1 Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.D);
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

        try {
            p1Buttons = new Buttons(PIN.D5, PIN.D6, PIN.D26, PIN.D21, PIN.D20);
            p1Buttons.obenDown(() -> getInput().mockKeyPress(KeyCode.Q));
            p1Buttons.untenDown(() -> getInput().mockKeyPress(KeyCode.R));
            p1Buttons.linksDown(() -> System.out.println("Hello from Steckdose"));
        } catch (Exception ignored) {
            System.out.println("failed to initialize buttons, proceeding");
        }

        getInput().addAction(new UserAction("open puzzle") {
            @Override
            protected void onAction() {
                boolean p1 = player1.getComponent(PlayerComponent.class).getIsNearDesk();
                boolean p2 = player2.getComponent(PlayerComponent.class).getIsNearDesk();
                if (p1 || p2) {
                    getSceneService().pushSubScene(new PuzzleSubScene());
                }
            }
        }, KeyCode.Q);

        onKeyDown(KeyCode.F, () -> {
            var lines = getAssetLoader().loadText("example—cutscene1.txt");
            var cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        });

        getInput().addAction(new UserAction("next room") {
            @Override
            protected void onAction() {
                boolean p1 = player1.isColliding(door);
                boolean p2 = player2.isColliding(door);
                if (p1 || p2) {
                    getGameScene().getViewport().fade(() -> nextLevel());
                }
            }
        }, KeyCode.R);
    }

    /**
     * This method initializes the physics engine that is used to handle collisions.
     */
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        physicsWorld.addCollisionHandler(new PlayerPlayerHandler());
        physicsWorld.addCollisionHandler(new PlayerDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerMainDeskHandler());
    }
}
