package ch.fhnw.strombewusst.input.pi4jcomponents.helpers;

public class ContinuousMeasuringException extends RuntimeException {

    public ContinuousMeasuringException(String message) {
        super(message);
    }

    public ContinuousMeasuringException(String message, Throwable cause) {
        super(message, cause);
    }
}
