package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

/**
 * Implements the game timer that counts backward from x to 0.
 * Also implements the inactivity timeout.
 */
public class Timer {
    private final StringProperty timer;
    private final IntegerProperty secondsRemaining;
    private final Timeline timeline;
    private int lastInteraction;
    private boolean stopped = false;

    public Timer(int initialSeconds) {
        timer = new SimpleStringProperty();
        secondsRemaining = new SimpleIntegerProperty(initialSeconds);

        lastInteraction = initialSeconds;
        secondsRemaining.addListener(((observable, oldValue, newValue) -> {
            if (lastInteraction - newValue.intValue() > Config.TIME_OUT_SECONDS
                    || newValue.intValue() == 0) {
                ((StromBewusst) FXGL.getApp()).endGame();
                pause();
            }
        }));

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
     * Stops the timer
     */
    public void stop() {
        stopped = true;
        timeline.stop();
    }

    /**
     * Unpauses the timer
     */
    public void unpause() {
        if (!stopped) {
            timeline.play();
        }
    }

    public IntegerProperty getSecondsRemainingProperty() {
        return secondsRemaining;
    }

    public StringProperty getTimerProperty() {
        return timer;
    }

    /**
     * Updates the last interaction counter to the current second
     */
    public void logInteraction() {
        lastInteraction = secondsRemaining.get();
    }
}
