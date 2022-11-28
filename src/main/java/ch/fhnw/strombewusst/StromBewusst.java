package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.ui.scene.MainMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;


public class StromBewusst extends GameApplication {
    private static final int PLAYER_SPEED = 5;
    private Entity player1;
    private Entity player2;
    private Entity desk;
    private Entity emptyRoom;

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
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new StromBewusstFactory());

        emptyRoom = FXGL.spawn("emptyRoom");
        player1 = FXGL.spawn("player1", FXGL.getAppCenter());
        player2 = FXGL.spawn("player2", 100, 100);
        desk = FXGL.spawn("desk");
    }

    @Override
    protected void initInput() {

        //Samuel
       /* FXGL.onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).up(PLAYER_SPEED));
        FXGL.onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).left(PLAYER_SPEED));
        FXGL.onKey(KeyCode.S, () -> player.getComponent(PlayerComponent.class).down(PLAYER_SPEED));
        FXGL.onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).right(PLAYER_SPEED));
    }*/

        // Player 1 Movement
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player1.getComponent(Player1AnimationComponent.class).moveRight();
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player1.getComponent(Player1AnimationComponent.class).moveLeft();
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player1.getComponent(Player1AnimationComponent.class).moveUp();
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player1.getComponent(Player1AnimationComponent.class).moveDown();
            }
        }, KeyCode.S);

        // Player 2 Movement
        FXGL.getInput().addAction(new UserAction("Right2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2AnimationComponent.class).moveRight();
            }
        }, KeyCode.L);
        FXGL.getInput().addAction(new UserAction("Left2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2AnimationComponent.class).moveLeft();
            }
        }, KeyCode.J);
        FXGL.getInput().addAction(new UserAction("Up2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2AnimationComponent.class).moveUp();
            }
        }, KeyCode.I);
        FXGL.getInput().addAction(new UserAction("Down2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2AnimationComponent.class).moveDown();
            }
        }, KeyCode.K);
    }


    //Collision Handler
    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.OBSTACLE) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity obstacle) {


            }
            @Override
            protected void onCollisionEnd(Entity player, Entity obstacle) {

            }

        });
    }

    //EntityTypes for Collision Handler
    public enum EntityType {
        PLAYER, OBSTACLE
    }









}
