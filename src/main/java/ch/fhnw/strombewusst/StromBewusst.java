package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerDoorHandler;
import ch.fhnw.strombewusst.collision.PlayerMainDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerPlayerHandler;
import ch.fhnw.strombewusst.input.Controller;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.rooms.Room;
import ch.fhnw.strombewusst.rooms.Room1;
import ch.fhnw.strombewusst.rooms.Room2;
import ch.fhnw.strombewusst.ui.scene.EndGameSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Map;

/**
 * This is the main class of our game, the methods of this class initialize the game.
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;
    private Entity door;
    private int level = 0;

    public static final Score SCORE = new Score();
    public static final QuizLogic QUIZ = new QuizLogic(10);

    private Room[] rooms;

    private Controller p1Controller;
    private Controller p2Controller;

    public static void main(String[] args) {
        launch(args);
    }

    void nextLevel() {
        level++;

        // BUGFIX: clear HBoxes on level change, so Desk info boxes don't persist
        List<Node> nodes = getSceneService().getCurrentScene().getContentRoot().getChildren();
        List<Node> hBoxes = nodes.stream().filter((n) -> n instanceof HBox).toList();
        for (Node n : hBoxes) {
            getSceneService().getCurrentScene().removeChild(n);
        }

        if (level <= rooms.length) {
            Room room = rooms[level - 1];

            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
            door = room.getDoor();

            getGameWorld().setLevel(room.getLevel());
        } else {
            level = 0;
            getGameController().gotoMainMenu();
            getSceneService().pushSubScene(new EndGameSubScene());
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
//this is a test
    /**
     * This method initializes the input. It gets executed *before* initGame.
     */
    @Override
    protected void initInput() {
        // Initializing the controllers, their input gets processed and the appropriate keypresses get "simulated"
        // By simulating the keypresses, the programm can be worked on locally by using WASD, but can be controlled
        // in production with the controller.
        try {
            p1Controller = new Controller(0, 1, PIN.D17, PIN.D5, PIN.D21, PIN.D26, PIN.D20, PIN.D6);
            //p2Controller = new Controller(2, 3, PIN.D6);

            p1Controller.onJoystickRight(() -> InputHandler.handlePlayerRight(player1));
            p1Controller.onJoystickLeft(() -> InputHandler.handlePlayerLeft(player1));
            p1Controller.onJoystickHorizontalIdle(() -> InputHandler.handlePlayerHorizontalIdle(player1));

            p1Controller.onJoystickUp(() -> InputHandler.handlePlayerUp(player1));
            p1Controller.onJoystickDown(() -> InputHandler.handlePlayerDown(player1));
            p1Controller.onJoystickVerticalIdle(() -> InputHandler.handlePlayerVerticalIdle(player1));

            p1Controller.onButtonUpperDown(() -> getInput().mockKeyPress(KeyCode.Q));
            p1Controller.onButtonLowerDown(() -> InputHandler.handleSelect(player1));
            p1Controller.onButtonLeftDown(() -> System.out.println("DEBUG: P1 LEFT DOWN"));
            p1Controller.onButtonMiddleDown(() -> System.out.println("DEBUG: P1 MIDDLE DOWN"));
            p1Controller.onButtonRightDown(() -> System.out.println("DEBUG: P1 RIGHT DOWN"));
            p1Controller.onButtonUpperDown(() -> System.out.println("DEBUG: P1 UPPER DOWN"));
            p1Controller.onButtonLowerDown(() -> System.out.println("DEBUG: P1 LOWER DOWN"));
/*
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
            });*/
        } catch (Exception ignored) {
            System.out.println("failed to initialize controller, proceeding");
        }

        // player1 Movement
        getInput().addAction(new UserAction("player1 Right") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerRight(player1);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerHorizontalIdle(player1);
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("player1 Left") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerLeft(player1);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerHorizontalIdle(player1);
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("player1 Up") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerUp(player1);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerVerticalIdle(player1);
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("player1 Down") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerDown(player1);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerVerticalIdle(player1);
            }
        }, KeyCode.S);

        // player2 Movement
        getInput().addAction(new UserAction("player2 Right") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerRight(player2);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerHorizontalIdle(player2);
            }
        }, KeyCode.L);
        getInput().addAction(new UserAction("player2 Left") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerLeft(player2);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerHorizontalIdle(player2);
            }
        }, KeyCode.J);
        getInput().addAction(new UserAction("player2 Up") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerUp(player2);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerVerticalIdle(player2);
            }
        }, KeyCode.I);
        getInput().addAction(new UserAction("player2 Down") {
            @Override
            protected void onAction() {
                InputHandler.handlePlayerDown(player2);
            }

            @Override
            protected void onActionEnd() {
                InputHandler.handlePlayerVerticalIdle(player2);
            }
        }, KeyCode.K);

        // "backwards compatibility"
        FXGL.onKeyDown(KeyCode.Q, () -> InputHandler.handleSelect(player1));
        FXGL.onKeyDown(KeyCode.R, () -> InputHandler.handleSelect(player1));

        onKeyDown(KeyCode.F, () -> {
            var lines = getAssetLoader().loadText("example—cutscene1.txt");
            var cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        });
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
        physicsWorld.addCollisionHandler(new PlayerDoorHandler());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score",0);
    }

    @Override
    protected void initUI() {
        var scoreText = getUIFactoryService().newText("", Color.ANTIQUEWHITE,38.0);
        scoreText.textProperty().bind(getip("score").asString("Score: %d"));
        addUINode(scoreText,720,60);
    }

    public int getLevel(){return level;}
}
