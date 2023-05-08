package ch.fhnw.strombewusst;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timer {
    private long startTime;
    private StringProperty timer;

    public Timer() {
        timer = new SimpleStringProperty("");
        startTime = System.currentTimeMillis();
    }

    /**
     * @return gives the timer stringproperty back
     */
    public StringProperty getTimerProperty() {
        return timer;
    }

    /**
     *updates the stringproperty
     */
    public void synchTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / 1000) / 60;
        timer.set(String.format("%02d:%02d", minutes, seconds));
    }
}
