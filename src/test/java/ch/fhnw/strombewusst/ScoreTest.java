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

    @Test
    void addMinBonus() {
        score.getScoreProperty().setValue(1000);

        score.addBonusPoints(100, 100);
        assertEquals(1000, score.getScore());
    }

    @Test
    void addMaxBonus() {
        score.getScoreProperty().setValue(1000);

        score.addBonusPoints(100, 0);
        assertEquals(1000 + 1000 * Score.MAX_BONUS_MULTIPLICATOR, score.getScore());
    }
}
