package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

import ch.fhnw.strombewusst.collision.PlayerDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerDoorHandler;
import ch.fhnw.strombewusst.collision.PlayerMainDeskHandler;
import ch.fhnw.strombewusst.collision.PlayerPlayerHandler;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import ch.fhnw.strombewusst.input.Controller;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.ui.scene.LeaderboardSubScene;
import ch.fhnw.strombewusst.ui.scene.MainMenu;
import ch.fhnw.strombewusst.ui.scene.PuzzleSubScene;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;

import java.util.Map;

/**
 * This is the main class of our game, the methods of this class initialize the game.
 */
public class StromBewusst extends GameApplication {
    private Entity player1;
    private Entity player2;

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
        spawn("wall", new SpawnData(0, getAppHeight()-30).put("width", (double) getAppWidth()).put("height", 0d));

        spawn("emptyRoom");
        player1 = spawn("player", new SpawnData(566,92).put("playerNum", 1));
        player2 = spawn("player", new SpawnData(694, 92).put("playerNum", 2));

        spawn("main-desk", 264,75);
        spawn("door",618,6);

        spawn("desk",new SpawnData(103,267).put("deskNum",0));
        spawn("desk",new SpawnData(264,267).put("deskNum",1));
        spawn("desk",new SpawnData(425,267).put("deskNum",2));
        spawn("desk",new SpawnData(586,267).put("deskNum",3));
        spawn("desk",new SpawnData(747,267).put("deskNum",4));

        spawn("desk",new SpawnData(103,405).put("deskNum",5));
        spawn("desk",new SpawnData(264,405).put("deskNum",6));
        spawn("desk",new SpawnData(425,405).put("deskNum",7));
        spawn("desk",new SpawnData(586,405).put("deskNum",8));
        spawn("desk",new SpawnData(747,405).put("deskNum",9));

        spawn("desk",new SpawnData(103,543).put("deskNum",10));
        spawn("desk",new SpawnData(264,543).put("deskNum",11));
        spawn("desk",new SpawnData(425,543).put("deskNum",12));
        spawn("desk",new SpawnData(586,543).put("deskNum",13));
        spawn("desk",new SpawnData(747,543).put("deskNum",14));
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

        getInput().addAction(new UserAction("open puzzle") {
            @Override
            protected void onAction() {
                boolean p1 = player1.getComponent(PlayerComponent.class).getIsNearDesk();
                boolean p2 = player2.getComponent(PlayerComponent.class).getIsNearDesk();
                if(p1||p2){
                    getSceneService().pushSubScene(new PuzzleSubScene());
                }
            }
        }, KeyCode.Q);
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
