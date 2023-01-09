package ch.fhnw.strombewusst.ui.scene;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleSubSceneTest{
    private PuzzleSubScene scene;
    private Robot robot;

    @BeforeEach
    public void setUp()throws AWTException{
        scene = new PuzzleSubScene();
        robot = new Robot();
    }

    @Test
    void getAnswerP1(){
        robot.keyPress(KeyEvent.VK_4);
        robot.keyRelease(KeyEvent.VK_4);
        assertEquals(scene.getAnswerP1(),"RED");
    }

    @Test
    void getAnswerP2() {
    }

    @Test
    void buildTextbox() {
    }

    @Test
    void clearQuiz() {
    }

    @Test
    void nextQuestion() {
    }

    @Test
    void buildQuiz() {
    }

}