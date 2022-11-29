package ch.fhnw.strombewusst;

import static com.almasb.fxgl.dsl.FXGL.*;

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
        getGameWorld().addEntityFactory(new StromBewusstFactory());

        spawn("wall", new SpawnData(20, 0).put("width", 0d).put("height", (double) getAppHeight()));
        spawn("wall", new SpawnData(0, 70).put("width", (double) getAppWidth()).put("height", 0d));
        spawn("wall", new SpawnData(890, 0).put("width", 0d).put("height", (double) getAppHeight()));
        spawn("wall", new SpawnData(0, getAppHeight()-30).put("width", (double) getAppWidth()).put("height", 0d));

        spawn("emptyRoom");
        player1 = spawn("player", new SpawnData(getAppCenter()).put("playerNum", 1));
        player2 = spawn("player", new SpawnData(100, 100).put("playerNum", 2));

        FXGL.spawn("main-desk", 264,75);
        FXGL.spawn("door",618,6);

        FXGL.spawn("desk",103,267);
        FXGL.spawn("desk",264,267);
        FXGL.spawn("desk",425,267);
        FXGL.spawn("desk",586,267);
        FXGL.spawn("desk",747,267);

        FXGL.spawn("desk",103,405);
        FXGL.spawn("desk",264,405);
        FXGL.spawn("desk",425,405);
        FXGL.spawn("desk",586,405);
        FXGL.spawn("desk",747,405);

        FXGL.spawn("desk",103,543);
        FXGL.spawn("desk",264,543);
        FXGL.spawn("desk",425,543);
        FXGL.spawn("desk",586,543);
        FXGL.spawn("desk",747,543);
    }

    @Override
    protected void initInput() {

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
    }


    //Collision Handler
    @Override
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();
        physicsWorld.setGravity(0, 0);

        physicsWorld.addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLAYER) {
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

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.DESK){
            Entity message1,message2;
            @Override
            protected void onCollisionBegin(Entity player, Entity desk){
                if(player == player1){
                    message1 = spawn("message",920,20);
                }
                else{
                    message2 = spawn("message",920,360);
                }

            }

            protected void onCollisionEnd(Entity player, Entity desk) {
                if(player == player1){
                    message1.removeFromWorld();
                }
                else{
                    message2.removeFromWorld();
                }
            }
        });
    }
}
