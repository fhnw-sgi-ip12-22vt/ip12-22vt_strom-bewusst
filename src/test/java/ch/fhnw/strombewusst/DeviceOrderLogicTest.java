package ch.fhnw.strombewusst;

import com.almasb.fxgl.app.services.FXGLAssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceOrderLogicTest {
    private static MockedStatic<FXGL> fxgl;

    private static DeviceOrderDevice[] devices = new DeviceOrderDevice[] {
        new DeviceOrderDevice("D1", 1, ""),
        new DeviceOrderDevice("D2", 2, ""),
        new DeviceOrderDevice("D3", 3, ""),
        new DeviceOrderDevice("D4", 4, ""),
        new DeviceOrderDevice("D5", 5, ""),
        new DeviceOrderDevice("D6", 6, "")
    };

    private static List<DeviceOrderDevice> deviceList = Arrays.stream(devices).toList();

    @InjectMocks
    private DeviceOrderLogic deviceOrderLogic;

    @BeforeAll
    static void setUpDSLMock() {
        FXGLAssetLoaderService assetLoaderService = mock(FXGLAssetLoaderService.class);
        when(assetLoaderService.loadJSON(anyString(), any())).thenReturn(Optional.of(devices));

        fxgl = Mockito.mockStatic(FXGL.class);
        fxgl.when(FXGL::getAssetLoader).thenReturn(assetLoaderService);
    }

    @BeforeEach
    void setUp() {
        deviceOrderLogic = new DeviceOrderLogic(1);
        deviceOrderLogic.initDevices();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    void ctorRoundsTotal(int value) {
        deviceOrderLogic = new DeviceOrderLogic(value);

        assertEquals(value, deviceOrderLogic.getRoundsLeft());
    }

    @Test
    void isDefaultAnswerWrong() {
        assertArrayEquals(new boolean[DeviceOrderLogic.QUEUESIZE], deviceOrderLogic.compareAnswerSolution());
    }

    @Test
    void isAnswerCorrectIterative() {
        for (int i = 0; i < DeviceOrderLogic.QUEUESIZE; i++) {
            assertFalse(deviceOrderLogic.compareAnswerSolution()[i]);
            deviceOrderLogic.addAnswer(devices[i]);
            assertTrue(deviceOrderLogic.compareAnswerSolution()[i]);
        }
    }

    @Test
    void getQueue() {
        var queue = deviceOrderLogic.getQueue();

        for (DeviceOrderDevice device : queue) {
            assertTrue(deviceList.contains(device));
        }
    }

    @Test
    void getIndex() {
        for (int i = 1; i <= DeviceOrderLogic.QUEUESIZE; i++) {
            deviceOrderLogic.addAnswer(devices[0]); // answer doesn't matter

            assertEquals(i, deviceOrderLogic.getIndex());
        }
    }

    @Test
    void clearAnswer() {
        for (int i = 0; i < DeviceOrderLogic.QUEUESIZE; i++) {
            deviceOrderLogic.addAnswer(devices[i]);
            assertTrue(deviceOrderLogic.compareAnswerSolution()[i]);
        }

        deviceOrderLogic.clearAnswerQueue();
        assertArrayEquals(new boolean[DeviceOrderLogic.QUEUESIZE], deviceOrderLogic.compareAnswerSolution());
    }

    @AfterAll
    static void tearDown() {
        fxgl.close();
    }
}
