package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerDoorHandler;
import ch.fhnw.strombewusst.collision.PlayerMainDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerPlayerHandler;
import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.input.Controller;
import ch.fhnw.strombewusst.input.KeyPressHandler;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;

/**
 * This is the main class of our game, the methods of this class initialize the game.
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;
    private Entity door1;
    private int room = 0;

    public static void main(String[] args) {
        launch(args);

    }

    public void setNextRoom() {
        room++;
        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
        initGame();
    }

    public void resetRooms() {
        room = 0;
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
        //spawn start room
        if (room == 0) {
            //start room must add Factory. following rooms not
            getGameWorld().addEntityFactory(new StromBewusstFactory());

            //spawn game boundaries & background
            spawn("wall", new SpawnData(20, 0).put("width", 0d).put("height", (double) getAppHeight()));
            spawn("wall", new SpawnData(0, 50).put("width", (double) getAppWidth()).put("height", 0d));
            spawn("wall", new SpawnData(890, 0).put("width", 0d).put("height", (double) getAppHeight()));
            spawn("wall", new SpawnData(0, getAppHeight() - 30).put("width", (double) getAppWidth()).put("height", 0d));
            spawn("emptyRoom");

            //spawn players
            player1 = spawn("player", new SpawnData(566, 92).put("playerNum", 1));
            player2 = spawn("player", new SpawnData(694, 92).put("playerNum", 2));

            //spawn room elements
            FXGL.spawn("main-desk", 264, 75);
            FXGL.spawn("door", 618, 6);

            for (int i = 0; i<3; i++){
                for (int j=0; j<5; j++){
                    FXGL.spawn("desk", new SpawnData(103+j*161, 267+i*138).put("deskNum", j+i*(1+j)));
                }
            }

        }

        //spawn end room
        if (room == 1) {
            //spawn game boundaries & background
            spawn("wall", new SpawnData(20, 0).put("width", 0d).put("height", (double) getAppHeight()));
            spawn("wall", new SpawnData(0, 50).put("width", (double) getAppWidth()).put("height", 0d));
            spawn("wall", new SpawnData(890, 0).put("width", 0d).put("height", (double) getAppHeight()));
            spawn("wall", new SpawnData(0, getAppHeight() - 30).put("width", (double) getAppWidth()).put("height", 0d));
            spawn("emptyRoom");

            //spawn players
            player1 = spawn("player", new SpawnData(603, 625).put("playerNum", 1));
            player2 = spawn("player", new SpawnData(656, 625).put("playerNum", 2));

            //spawn room elements
            FXGL.spawn("prev-door", 618, getAppHeight() - 127);
            door1 = FXGL.spawn("door", new SpawnData(618, 6).put("state",0));
        }

        if (room == 2) {
            getSceneService().pushSubScene(new EndGameScene());
            resetRooms();
        }

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
            Controller p1Controller = new Controller(0, 1, PIN.D17);
            Controller p2Controller = new Controller(2, 3, PIN.D6);

            p1Controller.onJoystickRight(() -> getInput().mockKeyPress(KeyCode.D));
            p1Controller.onJoystickLeft(() -> getInput().mockKeyPress(KeyCode.A));
            p1Controller.onJoystickHorizontalIdle(() -> {
                getInput().mockKeyRelease(KeyCode.A);
                getInput().mockKeyRelease(KeyCode.D);
            });

            p1Controller.onJoystickUp(() -> getInput().mockKeyPress(KeyCode.W));
            p1Controller.onJoystickDown(() -> getInput().mockKeyPress(KeyCode.S));
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

        KeyPressHandler player1Right = new KeyPressHandler(getInput(), KeyCode.D, "player1 Right");
        player1Right.onAction(() -> player1.getComponent(PlayerComponent.class).moveRight());
        player1Right.onActionEnd(() -> player1.getComponent(PlayerComponent.class).stopMovingX());

        KeyPressHandler player1Left = new KeyPressHandler(getInput(), KeyCode.A, "player1 Left");
        player1Left.onAction(() -> player1.getComponent(PlayerComponent.class).moveLeft());
        player1Left.onActionEnd(() -> player1.getComponent(PlayerComponent.class).stopMovingX());

        KeyPressHandler player1Up = new KeyPressHandler(getInput(), KeyCode.W, "player1 Up");
        player1Up.onAction(() -> player1.getComponent(PlayerComponent.class).moveUp());
        player1Up.onActionEnd(() -> player1.getComponent(PlayerComponent.class).stopMovingY());

        KeyPressHandler player1Down = new KeyPressHandler(getInput(), KeyCode.S, "player1 Down");
        player1Down.onAction(() -> player1.getComponent(PlayerComponent.class).moveDown());
        player1Down.onActionEnd(() -> player1.getComponent(PlayerComponent.class).stopMovingY());

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
                boolean p1 = player1.getComponent(PlayerComponent.class).getIsNearDoor();
                boolean p2 = player2.getComponent(PlayerComponent.class).getIsNearDoor();
                if (p1 || p2) {
                    getGameScene().getViewport().fade(() -> setNextRoom());
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
        physicsWorld.addCollisionHandler(new PlayerDoorHandler());
        physicsWorld.addCollisionHandler(new PlayerDeskHandler());
        physicsWorld.addCollisionHandler(new PlayerMainDeskHandler());
    }


}
