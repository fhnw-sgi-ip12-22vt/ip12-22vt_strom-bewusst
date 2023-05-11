package ch.fhnw.strombewusst;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Score {
    public static final int QUIZ_MIN_POINTS = 100;
    public static final int QUIZ_MAX_POINTS = 300;
    public static final int QUIZ_PENALTY = 100;
    public static final int DEVICE_ORDER_MIN_POINTS = 1000;
    public static final int DEVICE_ORDER_MAX_POINTS = 2500;
    public static final int DEVICE_ORDER_PENALTY = 1000;
    public static final float MIN_BONUS_MULTIPLICATOR = 0f; // 0f -> no bonus points
    public static final float MAX_BONUS_MULTIPLICATOR = 1f; // 1f -> double the points
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Increases the score.
     * @param penalty the number of wrong answers by the players
     */
    public void increaseScoreByQuiz(int penalty) {
        int points = Math.max(QUIZ_MIN_POINTS, QUIZ_MAX_POINTS - (penalty * QUIZ_PENALTY));
        score.setValue(score.get() + points);
    }

    /**
     * Increases the score.
     * @param penalty the number of wrong answers by the players
     */
    public void increaseScoreByDeviceOrder(int penalty) {
        int points = Math.max(DEVICE_ORDER_MIN_POINTS, DEVICE_ORDER_MAX_POINTS - (penalty * DEVICE_ORDER_PENALTY));
        score.setValue(score.get() + points);
    }

    /**
     * Calculates the bonus points from the maximum time and the actual time and applies them to the current score.
     *
     * @param maxTime the maximum time (which would be mapped to {@value MIN_BONUS_MULTIPLICATOR}
     * @param actualTime the minimum time (which will be mapped between {@value MIN_BONUS_MULTIPLICATOR} and
     * {@value MAX_BONUS_MULTIPLICATOR}
     * @return the amount of bonus points added.
     */
    public int addBonusPoints(int maxTime, int actualTime) {
        float bonusMultiply = (((maxTime - actualTime) * (MAX_BONUS_MULTIPLICATOR - MIN_BONUS_MULTIPLICATOR))
                / maxTime);

        int bonusPoints = (int) (score.get() * bonusMultiply);
        score.setValue(score.get() + bonusPoints);

        return bonusPoints;
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public int getScore() {
        return score.intValue();
    }

    @Override
    public String toString() {
        return "Score: " + this.score.get();
    }
}
