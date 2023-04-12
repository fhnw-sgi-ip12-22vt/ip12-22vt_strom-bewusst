package ch.fhnw.strombewusst.input;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private static class CheckRunnable implements Runnable {
        public boolean hasrun = false;
        public void run() {
            hasrun = true;
        }
    }

    CheckRunnable joystickRight;
    CheckRunnable joystickLeft;
    CheckRunnable joystickUp;
    CheckRunnable joystickDown;
    CheckRunnable joystickXIdle;
    CheckRunnable joystickYIdle;

    Controller controller;

    @BeforeEach
    void resetTest() {
        joystickRight = new CheckRunnable();
        joystickLeft = new CheckRunnable();
        joystickUp = new CheckRunnable();
        joystickDown = new CheckRunnable();
        joystickXIdle = new CheckRunnable();
        joystickYIdle = new CheckRunnable();

        controller = new Controller();
        controller.onJoystickRight(joystickRight);
        controller.onJoystickLeft(joystickLeft);
        controller.onJoystickUp(joystickUp);
        controller.onJoystickDown(joystickDown);
        controller.onJoystickHorizontalIdle(joystickXIdle);
        controller.onJoystickVerticalIdle(joystickYIdle);
    }

    @Test
    void testJoystickRight() {
        controller.handleXMove(0.1);
        assertTrue(joystickRight.hasrun);
        assertFalse(joystickLeft.hasrun);
        assertFalse(joystickUp.hasrun);
        assertFalse(joystickDown.hasrun);
        assertFalse(joystickXIdle.hasrun);
        assertFalse(joystickYIdle.hasrun);
    }

    @Test
    void testJoystickLeft() {
        controller.handleXMove(0.9);
        assertFalse(joystickRight.hasrun);
        assertTrue(joystickLeft.hasrun);
        assertFalse(joystickUp.hasrun);
        assertFalse(joystickDown.hasrun);
        assertFalse(joystickXIdle.hasrun);
        assertFalse(joystickYIdle.hasrun);
    }

    @Test
    void testJoystickDown() {
        controller.handleYMove(0.1);
        assertFalse(joystickRight.hasrun);
        assertFalse(joystickLeft.hasrun);
        assertFalse(joystickUp.hasrun);
        assertTrue(joystickDown.hasrun);
        assertFalse(joystickXIdle.hasrun);
        assertFalse(joystickYIdle.hasrun);
    }

    @Test
    void testJoystickUp() {
        controller.handleYMove(0.9);
        assertFalse(joystickRight.hasrun);
        assertFalse(joystickLeft.hasrun);
        assertTrue(joystickUp.hasrun);
        assertFalse(joystickDown.hasrun);
        assertFalse(joystickXIdle.hasrun);
        assertFalse(joystickYIdle.hasrun);
    }

    @Test
    void testJoystickXIdle() {
        controller.handleXMove(0.5);
        assertFalse(joystickRight.hasrun);
        assertFalse(joystickLeft.hasrun);
        assertFalse(joystickUp.hasrun);
        assertFalse(joystickDown.hasrun);
        assertTrue(joystickXIdle.hasrun);
        assertFalse(joystickYIdle.hasrun);
    }

    @Test
    void testJoystickYIdle() {
        controller.handleYMove(0.5);
        assertFalse(joystickRight.hasrun);
        assertFalse(joystickLeft.hasrun);
        assertFalse(joystickUp.hasrun);
        assertFalse(joystickDown.hasrun);
        assertFalse(joystickXIdle.hasrun);
        assertTrue(joystickYIdle.hasrun);
    }
}
