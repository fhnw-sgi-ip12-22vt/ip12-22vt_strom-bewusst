package ch.fhnw.strombewusst;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timer {
    private long startTime;
    private StringProperty timer;
    private Thread timerThread;

    public Timer() {
        timer = new SimpleStringProperty("");
    }

    /**
     * starts the timer
     */
    public void startTimer() {
        startTime = System.currentTimeMillis();
        timerThread = new Thread(() -> synchTimer());
        timerThread.start();
    }

    /**
     *stops the timer
     */
    public void stopTimer() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    /**
     *continues the timer
     */
    public void continueTimer() {
        if (startTime != 0) {
            timerThread = new Thread(() -> synchTimer());
            timerThread.start();
        }
    }

    /**
     * @return gives the timer stringproperty back
     */
    public StringProperty timeProperty() {
        return timer;
    }

    /**
     *private method who updates the stringproperty timer every second
     */
    private void synchTimer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long seconds = (elapsedTime / 1000) % 60;
                long minutes = (elapsedTime / 1000) / 60;
                timer.set(String.format("%02d:%02d", minutes, seconds));
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }
}
