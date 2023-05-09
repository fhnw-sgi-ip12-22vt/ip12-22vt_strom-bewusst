package ch.fhnw.strombewusst;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Timer {

    StringProperty timer;
    long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
        timer = new SimpleStringProperty("");
    }

    /**
     * @return stringproperty
     */
    public StringProperty getTimerProperty() {
        return timer;
    }

    /**
     * updates time
     */
    public void synchTimer() {
        long secondsElapsed = (System.currentTimeMillis() - startTime) / 1000;
        long secondsRemaining = 900 - secondsElapsed;
        if (secondsRemaining < 0) {
            secondsRemaining = 0;
        }
        long minutes = secondsRemaining / 60;
        long seconds = secondsRemaining % 60;
        timer.set(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * control for subscenes
     * @param x x-coordinate
     * @param y y-coordinate
     * @return the hbox layout
     */
    public HBox pushTimer(int x, int y) {
        Text title = new Text();
        title.textProperty().bindBidirectional(timer);
        title.setStyle("-fx-font-size: 44px;");
        HBox titleHBox1 = new HBox(title);
        titleHBox1.setTranslateX(x);
        titleHBox1.setTranslateY(y);
        return titleHBox1;
    }
}
