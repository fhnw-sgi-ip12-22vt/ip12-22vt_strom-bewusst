package ch.fhnw.strombewusst;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Score {
    private static final int QUIZ_MIN_POINTS = 1;
    private static final int QUIZ_MAX_POINT = 3;
    private static final int QUIZ_PENALTY = 1;
    private final IntegerProperty score;

    private int queueSolved;

    public Score() {
        this.score = new SimpleIntegerProperty(0);
        this.queueSolved = 0;
    }

    /**
     * Increases the score.
     * @param penalty the number of wrong answers by the players
     */
    public void increaseScoreByQuiz(int penalty) {
        int points = Math.max(QUIZ_MIN_POINTS, QUIZ_MAX_POINT - (penalty * QUIZ_PENALTY));
        score.setValue(score.get() + points);
    }

    /**
     * Increases the score.
     * @param points the amount of points to increase the score by
     */
    public void increaseScoreByDeviceOrder(int points) {
        score.setValue(score.get() + points);
        this.queueSolved++;
    }

    /**
     * Generates a text label containing the score with the required styling.
     * @param x x coordinate of the HBox containing the label
     * @param y y coordinate of the HBox containing the label
     * @return the generated HBox
     */
    public HBox pushScore(int x, int y) {
        Text title = new Text(toString());
        title.setStyle("-fx-font-size: 44px;");
        HBox titleHBox1 = new HBox(title);
        titleHBox1.setTranslateX(x);
        titleHBox1.setTranslateY(y);
        return titleHBox1;
    }

    public int getQueueSolved() {
        return queueSolved;
    }

    public IntegerProperty getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Score: " + this.score.get();
    }
}
