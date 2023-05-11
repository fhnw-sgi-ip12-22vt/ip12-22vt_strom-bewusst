package ch.fhnw.strombewusst;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class Timer {
    private final StringProperty timer;
    private final IntegerProperty secondsRemaining;
    private final Timeline timeline;

    public Timer(int initialSeconds) {
        timer = new SimpleStringProperty();
        secondsRemaining = new SimpleIntegerProperty(initialSeconds);

        timer.bind(Bindings.createStringBinding(() -> {
            int minutes = secondsRemaining.get() / 60;
            int secs = secondsRemaining.get() % 60;
            return String.format("%02d:%02d", minutes, secs);
        }, secondsRemaining));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                event -> secondsRemaining.set(secondsRemaining.get() - 1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Pauses the timer
     */
    public void pause() {
        timeline.pause();
    }

    /**
     * Unpauses the timer
     */
    public void unpause() {
        timeline.play();
    }

    public IntegerProperty getSecondsRemainingProperty() {
        return secondsRemaining;
    }

    public StringProperty getTimerProperty() {
        return timer;
    }
}
