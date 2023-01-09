package ch.fhnw.strombewusst.input;

import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ButtonsTest {
    private Buttons button;

    @BeforeEach
    public void setUp() {
        button = new Buttons(PIN.D5, PIN.D6, PIN.D26, PIN.D21, PIN.D20);
    }

    @Test
    void linksDown() {
    }

    @Test
    void rechtsDown() {
    }

    @Test
    void mitteDown() {
    }

    @Test
    void obenDown() {
    }

    @Test
    void untenDown() {
    }
}