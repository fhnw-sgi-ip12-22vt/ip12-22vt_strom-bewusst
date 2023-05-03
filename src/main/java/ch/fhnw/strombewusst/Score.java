package ch.fhnw.strombewusst;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Score {
    private final IntegerProperty score;
    private int answerSolved;

    private int queueSolved;

    public Score() {
        this.score = new SimpleIntegerProperty(0);
        this.answerSolved = 0;
        this.queueSolved = 0;
    }

    /**
     * Increases the score.
     * @param points the amount of points to increase the score by
     */
    public void increaseScoreByQuiz(int points) {
        score.setValue(score.get() + points);
        this.answerSolved++;
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

    public int getAnswerSolved() {
        return answerSolved;
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
