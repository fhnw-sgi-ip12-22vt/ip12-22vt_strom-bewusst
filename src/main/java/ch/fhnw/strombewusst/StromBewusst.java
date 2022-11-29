package ch.fhnw.strombewusst;

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
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import javafx.scene.input.KeyCode;


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

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new StromBewusstFactory());

        FXGL.spawn("wall", new SpawnData(0, 0).put("width", 0d).put("height", (double)FXGL.getAppHeight()));
        FXGL.spawn("wall", new SpawnData(0, 0).put("width", (double)FXGL.getAppWidth()).put("height", 0d));
        FXGL.spawn("wall", new SpawnData(FXGL.getAppWidth(), 0).put("width", 0d).put("height", (double)FXGL.getAppHeight()));
        FXGL.spawn("wall", new SpawnData(0, FXGL.getAppHeight()).put("width", (double)FXGL.getAppWidth()).put("height", 0d));

        FXGL.spawn("emptyRoom");
        player1 = FXGL.spawn("player", new SpawnData(FXGL.getAppCenter()).put("playerNum",1));
        player2 = FXGL.spawn("player", new SpawnData(100,100).put("playerNum",2));
        FXGL.spawn("desk",100,200);
        FXGL.spawn("desk",200,200);
        FXGL.spawn("desk",300,200);
        FXGL.spawn("desk",400,200);
    }

    @Override
    protected void initInput() {

        // player1 Movement
        FXGL.getInput().addAction(new UserAction("player1 Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveRight();
            }
            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("player1 Left") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveLeft();
            }
            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("player1 Up") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).moveUp();
            }
            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.W);
        FXGL.getInput().addAction(new UserAction("player1 Down") {
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
        FXGL.getInput().addAction(new UserAction("player2 Right") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveRight();
            }
            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.L);
        FXGL.getInput().addAction(new UserAction("player2 Left") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveLeft();
            }
            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingX();
            }
        }, KeyCode.J);
        FXGL.getInput().addAction(new UserAction("player2 Up") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).moveUp();
            }
            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stopMovingY();
            }
        }, KeyCode.I);
        FXGL.getInput().addAction(new UserAction("player2 Down") {
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


    //Collision Handler
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = FXGL.getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLAYER) {
            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                super.onCollisionEnd(a, b);
                // resetting velocity so pushed players don't keep moving after collision
                a.getComponent(PhysicsComponent.class).setVelocityX(0);
                a.getComponent(PhysicsComponent.class).setVelocityY(0);
                b.getComponent(PhysicsComponent.class).setVelocityX(0);
                b.getComponent(PhysicsComponent.class).setVelocityY(0);
            }
        });
    }
}
