package ch.fhnw.strombewusst;

import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SceneService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class InputHandlerPlayerMovementTest {
    private static MockedStatic<FXGL> fxgl;

    private static SceneService sceneService;

    private Entity playerEntity;
    private PlayerComponent playerComponent;


    @BeforeAll
    static void setUpDSLMock() {
        sceneService = mock(SceneService.class);

        fxgl = Mockito.mockStatic(FXGL.class);
        fxgl.when(FXGL::getSceneService).thenReturn(sceneService);
    }


    @BeforeEach
    void setUp() {
        playerEntity = mock(Entity.class);
        playerComponent = mock(PlayerComponent.class);
        when(playerEntity.getComponent(PlayerComponent.class)).thenReturn(playerComponent);
    }

    @Test
    void testJoystickRight() {
        InputHandler.handlePlayerRight(playerEntity);
        InputHandler.handlePlayerHorizontalIdle(playerEntity);

        InOrder inOrder = inOrder(playerComponent);
        inOrder.verify(playerComponent, times(1)).moveRight();
        inOrder.verify(playerComponent, times(0)).moveLeft();
        inOrder.verify(playerComponent, times(0)).moveUp();
        inOrder.verify(playerComponent, times(0)).moveDown();
        inOrder.verify(playerComponent, times(1)).stopMovingX();
        inOrder.verify(playerComponent, times(0)).stopMovingY();
    }

    @Test
    void testJoyStickLeft() {
        InputHandler.handlePlayerLeft(playerEntity);
        InputHandler.handlePlayerHorizontalIdle(playerEntity);

        InOrder inOrder = inOrder(playerComponent);
        inOrder.verify(playerComponent, times(0)).moveRight();
        inOrder.verify(playerComponent, times(1)).moveLeft();
        inOrder.verify(playerComponent, times(0)).moveUp();
        inOrder.verify(playerComponent, times(0)).moveDown();
        inOrder.verify(playerComponent, times(1)).stopMovingX();
        inOrder.verify(playerComponent, times(0)).stopMovingY();
    }

    @Test
    void testJoyStickUp() {
        GameScene scene = mock(GameScene.class);
        when(sceneService.getCurrentScene()).thenReturn(scene);

        InputHandler.handlePlayerUp(playerEntity);
        InputHandler.handlePlayerVerticalIdle(playerEntity);

        InOrder inOrder = inOrder(playerComponent);
        inOrder.verify(playerComponent, times(0)).moveRight();
        inOrder.verify(playerComponent, times(0)).moveLeft();
        inOrder.verify(playerComponent, times(1)).moveUp();
        inOrder.verify(playerComponent, times(0)).moveDown();
        inOrder.verify(playerComponent, times(0)).stopMovingX();
        inOrder.verify(playerComponent, times(1)).stopMovingY();
    }

    @Test
    void testJoyStickDown() {
        GameScene scene = mock(GameScene.class);
        when(sceneService.getCurrentScene()).thenReturn(scene);

        InputHandler.handlePlayerDown(playerEntity);
        InputHandler.handlePlayerVerticalIdle(playerEntity);

        InOrder inOrder = inOrder(playerComponent);
        inOrder.verify(playerComponent, times(0)).moveRight();
        inOrder.verify(playerComponent, times(0)).moveLeft();
        inOrder.verify(playerComponent, times(0)).moveUp();
        inOrder.verify(playerComponent, times(1)).moveDown();
        inOrder.verify(playerComponent, times(0)).stopMovingX();
        inOrder.verify(playerComponent, times(1)).stopMovingY();
    }

    @AfterAll
    static void tearDown() {
        fxgl.close();
    }
}
