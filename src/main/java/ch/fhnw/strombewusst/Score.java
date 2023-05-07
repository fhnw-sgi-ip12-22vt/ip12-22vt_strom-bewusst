package ch.fhnw.strombewusst;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Score {
    public static final int QUIZ_MIN_POINTS = 1;
    public static final int QUIZ_MAX_POINTS = 3;
    public static final int QUIZ_PENALTY = 1;
    public static final int DEVICE_ORDER_MIN_POINTS = 10;
    public static final int DEVICE_ORDER_MAX_POINTS = 25;
    public static final int DEVICE_ORDER_PENALTY = 10;
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
