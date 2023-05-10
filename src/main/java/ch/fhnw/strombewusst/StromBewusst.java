package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.collision.*;
import ch.fhnw.strombewusst.input.Controller;
import ch.fhnw.strombewusst.input.pi4jcomponents.Ads1115;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.rooms.EndgameScene;
import ch.fhnw.strombewusst.rooms.Room;
import ch.fhnw.strombewusst.rooms.Room1;
import ch.fhnw.strombewusst.rooms.Room2;
import ch.fhnw.strombewusst.ui.scene.EndGameSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

/**
 * This is the main class of our game, the methods of this class initialize and launch the game.
 * The initialization process can be seen below (irrelevant phases are omitted):
 *
 * <ol>
 * <li>Instance fields of YOUR subclass of GameApplication</li>
 * <li>initSettings()</li>
 * <li>Services configuration (after this you can safely call any FXGL.* methods)</li>
 * <b>Executed on JavaFX UI thread:</b>
 * <li>initInput()</li>
 * <li>onPreInit()</li>
 * <b>NOT executed on JavaFX UI thread:</b>
 * <li>initGameVars()</li>
 * <li>initGame()</li>
 * <li>initPhysics()</li>
 * <li>initUI()</li>
 * <b>Start of main game loop execution on JavaFX UI thread</b>
 * </ol>
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;
    private Entity door;
    private int level = 0;

    private Room[] rooms;

    private Controller p1Controller;
    private Controller p2Controller;

    /**
     * Main Method, launches the FXGL application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads the next level, or ends the game if the current level is the last one.
     */
    public void nextLevel() {
        level++;

        FXGL.set("player1InfoText", "");
        FXGL.set("player2InfoText", "");

        if (level <= rooms.length) {
            Room room = rooms[level - 1];

            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
            door = room.getDoor();

            FXGL.getGameWorld().setLevel(room.getLevel());
            room.onStarted();
        } else {
            level = 0;
            FXGL.getGameController().gotoMainMenu();
            FXGL.getSceneService().pushSubScene(new EndGameSubScene());
        }
    }

    /**
     * Saves the Leaderboard and exits to the main menu.
     * @param teamName the team name to save the score under
     */
    public void endGame(String teamName) {
        FXGL.getService(HighScoreService.class).commit(teamName);
        FXGL.getSaveLoadService().saveAndWriteTask(Config.SAVE_FILE_NAME).run();
        FXGL.getGameController().gotoMainMenu();
    }

    /**
     * Initializes the game settings.
     * @param settings The game settings object
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Strom Bewusst");
        settings.setVersion("0.1_BETA");
        settings.getCSSList().add("main.css");

        settings.setApplicationMode(Config.IS_RELEASE ? ApplicationMode.RELEASE : ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(!Config.IS_RELEASE);

        // BUGFIX: explicitly setting the TPS fixes the inconsistent movement speed
        // The TPS also limits the FPS, so a small value can make the game look bad. 60 TPS works well for development
        // but might need to get lowered for better performance on the raspberry.
        settings.setTicksPerSecond(Config.TICK_RATE);

        if (System.getProperty("os.name").equals("Linux")) {
            settings.setFullScreenAllowed(true);
            settings.setFullScreenFromStart(true);

            settings.setDefaultCursor(new CursorInfo("hiddencursor.png", 0, 0));
        }

        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(false);
        settings.setWidth(Config.WIDTH);
        settings.setHeight(Config.HEIGHT);

        settings.addEngineService(HighScoreService.class);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    /**
     * Initializes the game (after the "Play" Button on the Leaderboard is pressed). To start a new game, quizzes are
     * reset and the rooms (-> entities) are regenerated.
     */
    @Override
    protected void initGame() {
        FXGL.<QuizLogic>geto("quizLogic").initQuestions();
        FXGL.<DeviceOrderLogic>geto("deviceOrderLogic").initDevices();

        FXGL.<Score>geto("score").getScoreProperty().addListener((observable, oldValue, newValue) ->
                FXGL.getService(HighScoreService.class).setScore((int) newValue));

        FXGL.getGameWorld().addEntityFactory(new StromBewusstFactory());
        rooms = new Room[] {new Room1(), new Room2(), new EndgameScene()};
        level = 0;
        nextLevel();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            FXGL.<Timer>geto("timer").synchTimer();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Initializes the input. It gets executed *before* initGame.
     */
    @Override
    protected void initInput() {
        try {
            // PIN.D17 and PIN.D4 are dummy pins, they would be used for the joystick button, which we do not use.
            // despite not using them, they have to be set to *some* value, so we just use unused pins

            Ads1115 p1ads1115 = new Ads1115(Controller.PI4J_CONTEXT, 0x01, Ads1115.GAIN.GAIN_4_096V,
                    Ads1115.ADDRESS.GND, 2);
            p1Controller = new Controller(p1ads1115, 0, 1, PIN.D17, PIN.D6, PIN.D20, PIN.D26, PIN.D5, PIN.D21);
            Ads1115 p2ads1115 = new Ads1115(Controller.PI4J_CONTEXT, 0x01, Ads1115.GAIN.GAIN_4_096V,
                    Ads1115.ADDRESS.VDD, 2);
            p2Controller = new Controller(p2ads1115, 0, 1, PIN.D4, PIN.D25, PIN.PWM18, PIN.D24, PIN.PWM19, PIN.PWM12);

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
            Logger.get(StromBewusst.class).warning("failed to initialize controller, proceeding");
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
    }

    /**
     * Creates a UserAction using the two provided runnable objects.
     *
     * @param name        Name of the UserAction
     * @param onAction    Runnable to be executed onAction
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
     * Initializes the physics engine that is used to handle collisions and movement.
     */
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = FXGL.getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        physicsWorld.addCollisionHandler(new PlayerPlayerHandler());
        physicsWorld.addCollisionHandler(new PlayerDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerDeviceHandler());
        physicsWorld.addCollisionHandler(new PlayerMainDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerDoorHandler());
    }

    /**
     * Initializes the game variables. This method gets executed before every game.
     * @param vars The game vars map
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", new Score());
        vars.put("timer", new Timer());
        vars.put("player1InfoText", "");
        vars.put("player2InfoText", "");

        vars.put("quizLogic", new QuizLogic(Config.QUIZ_QUESTION_COUNT));
        vars.put("deviceOrderLogic", new DeviceOrderLogic(Config.DEVICE_ORDER_COUNT));
    }

    /**
     * Initializes the UI. In this method UI elements such as the score get created and game vars bound to them.
     */
    @Override
    protected void initUI() {
        Text scoreText = FXGL.getUIFactoryService().newText("", Color.ANTIQUEWHITE, 38.0);
        scoreText.textProperty().bind(FXGL.<Score>geto("score").getScoreProperty().asString("%d"));
        FXGL.addUINode(scoreText, 810, 67);

        Text timerText = FXGL.getUIFactoryService().newText("", Color.ANTIQUEWHITE, 38.0);
        timerText.textProperty().bind(FXGL.<Timer>geto("timer").getTimerProperty());
        FXGL.addUINode(timerText, 405, 65);

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

    /**
     * Gets executed once per application lifetime, before initGame.
     * In this method, assets are cached to decrease game startup delay, and the exception handler is overridden.
     */
    @Override
    protected void onPreInit() {
        // loading assets before the game caches them, which reduces loading times when starting a new game
        FXGL.getAssetLoader().loadJSON(Config.QUESTIONS_JSON_PATH, QuizQuestion[].class);
        FXGL.getAssetLoader().loadJSON(Config.DEVICES_JSON_PATH, DeviceOrderDevices[].class);
        FXGL.getAssetLoader().loadText(Config.ROOM_1_TEXT_PATH);
        FXGL.getAssetLoader().loadText(Config.ROOM_2_TEXT_PATH);

        // overwriting the default exception handler, so the game gets automatically restarted if an uncaught exception
        // occurs.
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            Logger log = Logger.get(StromBewusst.class);
            log.fatal("Uncaught Exception:", exception);
            log.fatal("Fatal Error, restarting game");

            FXGL.getGameController().gotoMainMenu();

            // Pushing a notification so the user has an indication of what happened
            // This looks very weird, since notifications are animated and look a lot like what an achievement.
            // Despite this, it's still better than nothing at all.
            var icon = new Text("⚠");
            icon.setFont(Font.font("Verdana", FontWeight.BLACK, FontPosture.REGULAR, 32));
            icon.setFill(Color.WHITE);
            icon.setY(30);
            FXGL.getNotificationService().setBackgroundColor(Color.CRIMSON);
            FXGL.getNotificationService().pushNotification("FATAL ERROR! Application restarted", icon);
        });
    }

    public int getLevel() {
        return level;
    }
}
