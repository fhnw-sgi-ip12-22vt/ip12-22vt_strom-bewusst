package ch.fhnw.strombewusst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void checkDefaultScoreIsZero() {
        assertEquals(0, score.getScore());
    }

    @Test
    void increaseScoreByQuizMaxPoints() {
        score.increaseScoreByQuiz(0);
        assertEquals(Score.QUIZ_MAX_POINTS, score.getScore());
    }

    @Test
    void increaseScoreByQuizMinPoints() {
        score.increaseScoreByQuiz((Score.QUIZ_MAX_POINTS - Score.QUIZ_MIN_POINTS) / Score.QUIZ_PENALTY + 1);
        assertEquals(Score.QUIZ_MIN_POINTS, score.getScore());
    }

    @Test
    void increaseScoreByDeviceOrderMaxPoints() {
        score.increaseScoreByDeviceOrder(0);
        assertEquals(Score.DEVICE_ORDER_MAX_POINTS, score.getScore());
    }

    @Test
    void increaseScoreByDeviceOrderMinPoints() {
        score.increaseScoreByDeviceOrder((Score.DEVICE_ORDER_MAX_POINTS - Score.DEVICE_ORDER_MIN_POINTS)
                / Score.DEVICE_ORDER_PENALTY + 1);
        assertEquals(Score.DEVICE_ORDER_MIN_POINTS, score.getScore());
    }
}
