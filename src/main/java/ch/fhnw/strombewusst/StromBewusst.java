package ch.fhnw.strombewusst;

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
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

    public static final DeviceOrderLogic DEVICES = new DeviceOrderLogic(2);

    private Room[] rooms;

    private Controller p1Controller;
    private Controller p2Controller;

    public static void main(String[] args) {
        launch(args);
    }

    void nextLevel() {
        level++;

        FXGL.set("player1InfoText", "");
        FXGL.set("player2InfoText", "");

        if (level <= rooms.length) {
            Room room = rooms[level - 1];

            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
            door = room.getDoor();

            FXGL.getGameWorld().setLevel(room.getLevel());
        } else {
            level = 0;
            FXGL.getGameController().gotoMainMenu();
            FXGL.getSceneService().pushSubScene(new EndGameSubScene());
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
        QuizLogic quiz = new QuizLogic(10);
        FXGL.set("quizLogic", quiz);
        quiz.initQuestions();

        DEVICES.initDevices();
        FXGL.getGameWorld().addEntityFactory(new StromBewusstFactory());
        rooms = new Room[] {new Room1(), new Room2()};
        level = 0;
        nextLevel();
    }

    /**
     * This method initializes the input. It gets executed *before* initGame.
     */
    @Override
    protected void initInput() {
        try {
            // PIN.D17 and PIN.D4 are dummy pins, they would be used for the joystick button, which we do not use.
            // despite not using them, they have to be set to *some* value, so we just use unused pins
            p1Controller = new Controller(0, 1, PIN.D17, PIN.D5, PIN.D21, PIN.D26, PIN.D20, PIN.D6);
            p2Controller = new Controller(2, 3, PIN.D4, PIN.PWM19, PIN.PWM12, PIN.D24, PIN.PWM18, PIN.D25);

            // Player 1 controls
            p1Controller.onJoystickRight(() -> InputHandler.handlePlayerRight(player1));
            p1Controller.onJoystickLeft(() -> InputHandler.handlePlayerLeft(player1));
            p1Controller.onJoystickHorizontalIdle(() -> InputHandler.handlePlayerHorizontalIdle(player1));

            p1Controller.onJoystickUp(() -> InputHandler.handlePlayerUp(player1));
            p1Controller.onJoystickDown(() -> InputHandler.handlePlayerDown(player1));
            p1Controller.onJoystickVerticalIdle(() -> InputHandler.handlePlayerVerticalIdle(player1));

            p1Controller.onButtonLeftDown(() -> InputHandler.handleButtonLeft(player1));
            p1Controller.onButtonMiddleDown(() -> InputHandler.handleButtonMiddle(player1));
            p1Controller.onButtonRightDown(() -> InputHandler.handleButtonRight(player1));
            p1Controller.onButtonLowerDown(() -> InputHandler.handleSelect(player1));
            p1Controller.onButtonUpperDown(() -> InputHandler.handleBack(player1));

            // Player 2 controls
            p2Controller.onJoystickRight(() -> InputHandler.handlePlayerRight(player2));
            p2Controller.onJoystickLeft(() -> InputHandler.handlePlayerLeft(player2));
            p2Controller.onJoystickHorizontalIdle(() -> InputHandler.handlePlayerHorizontalIdle(player2));

            p2Controller.onJoystickUp(() -> InputHandler.handlePlayerUp(player2));
            p2Controller.onJoystickDown(() -> InputHandler.handlePlayerDown(player2));
            p2Controller.onJoystickVerticalIdle(() -> InputHandler.handlePlayerVerticalIdle(player2));

            p2Controller.onButtonLeftDown(() -> InputHandler.handleButtonLeft(player2));
            p2Controller.onButtonMiddleDown(() -> InputHandler.handleButtonMiddle(player2));
            p2Controller.onButtonRightDown(() -> InputHandler.handleButtonRight(player2));
            p2Controller.onButtonLowerDown(() -> InputHandler.handleSelect(player2));
            p2Controller.onButtonUpperDown(() -> InputHandler.handleBack(player2));
        } catch (Exception ignored) {
            System.out.println("failed to initialize controller, proceeding");
        }

        // player1 Movement
        FXGL.getInput().addAction(getUserAction("player1 Right",
                        () -> InputHandler.handlePlayerRight(player1),
                        () -> InputHandler.handlePlayerHorizontalIdle(player1)),
                KeyCode.D);
        FXGL.getInput().addAction(getUserAction("player1 Left",
                        () -> InputHandler.handlePlayerLeft(player1),
                        () -> InputHandler.handlePlayerHorizontalIdle(player1)),
                KeyCode.A);
        FXGL.getInput().addAction(getUserAction("player1 Up",
                        () -> InputHandler.handlePlayerUp(player1),
                        () -> InputHandler.handlePlayerVerticalIdle(player1)),
                KeyCode.W);
        FXGL.getInput().addAction(getUserAction("player1 Down",
                        () -> InputHandler.handlePlayerDown(player1),
                        () -> InputHandler.handlePlayerVerticalIdle(player1)),
                KeyCode.S);


        // player1 Movement
        FXGL.getInput().addAction(getUserAction("player2 Right",
                        () -> InputHandler.handlePlayerRight(player2),
                        () -> InputHandler.handlePlayerHorizontalIdle(player2)),
                KeyCode.L);
        FXGL.getInput().addAction(getUserAction("player2 Left",
                        () -> InputHandler.handlePlayerLeft(player2),
                        () -> InputHandler.handlePlayerHorizontalIdle(player2)),
                KeyCode.J);
        FXGL.getInput().addAction(getUserAction("player2 Up",
                        () -> InputHandler.handlePlayerUp(player2),
                        () -> InputHandler.handlePlayerVerticalIdle(player2)),
                KeyCode.I);
        FXGL.getInput().addAction(getUserAction("player2 Down",
                        () -> InputHandler.handlePlayerDown(player2),
                        () -> InputHandler.handlePlayerVerticalIdle(player2)),
                KeyCode.K);

        // "backwards compatibility"
        FXGL.onKeyDown(KeyCode.Q, () -> InputHandler.handleSelect(player1));
        FXGL.onKeyDown(KeyCode.R, () -> InputHandler.handleSelect(player1));

        FXGL.onKeyDown(KeyCode.F, () -> {
            var lines = FXGL.getAssetLoader().loadText("example—cutscene1.txt");
            var cutscene = new Cutscene(lines);
            FXGL.getCutsceneService().startCutscene(cutscene);
        });
    }

    /**
     * Returns an userAction with running the runnables provided
     *
     * @param name Name of the UserAction
     * @param onAction Runnable to be execuded onAction
     * @param onActionEnd Runnable to be executed onActionEnd
     * @return The constructed UserAction
     */
    private UserAction getUserAction(String name, Runnable onAction, Runnable onActionEnd) {
        return new UserAction(name) {
            @Override
            protected void onAction() {
                onAction.run();
            }

            @Override
            protected void onActionEnd() {
                onActionEnd.run();
            }
        };
    }

    /**
     * This method initializes the physics engine that is used to handle collisions.
     */
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = FXGL.getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        physicsWorld.addCollisionHandler(new PlayerPlayerHandler());
        physicsWorld.addCollisionHandler(new PlayerPlayerHandler());
        physicsWorld.addCollisionHandler(new PlayerDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerMainDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerDoorHandler());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("player1InfoText", "");
        vars.put("player2InfoText", "");

        vars.put("quizLogic", new QuizLogic(0));
    }

    @Override
    protected void initUI() {
        Text scoreText = FXGL.getUIFactoryService().newText("", Color.ANTIQUEWHITE, 38.0);
        scoreText.textProperty().bind(FXGL.getip("score").asString("%d"));
        FXGL.addUINode(scoreText, 810, 67);

        Text player1InfoText = new Text("");
        player1InfoText.setWrappingWidth(300);
        player1InfoText.setFont(Font.font("Verdana", FontWeight.BOLD, 15d));
        player1InfoText.textProperty().bind(FXGL.getsp("player1InfoText"));
        FXGL.addUINode(player1InfoText, 950, 45);

        Text player2InfoText = new Text("");
        player2InfoText.setWrappingWidth(300);
        player2InfoText.setFont(Font.font("Verdana", FontWeight.BOLD, 15d));
        player2InfoText.textProperty().bind(FXGL.getsp("player2InfoText"));
        FXGL.addUINode(player2InfoText, 950, 400);
    }

    public int getLevel() {
        return level;
    }
}
