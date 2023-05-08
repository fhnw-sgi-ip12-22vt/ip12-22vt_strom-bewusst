package ch.fhnw.strombewusst.input;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;

public class InputEvent extends Event {
    public static final EventType<InputEvent> MOVE = new EventType<>(Event.ANY, "MOVE_EVENT");

    private final InputType inputType;
    private final Entity player;

    public InputEvent(InputType inputType, Entity player) {
        super(MOVE);
        this.inputType = inputType;
        this.player = player;
    }

    public InputType getInput() {
        return inputType;
    }

    public Entity getPlayer() {
        return player;
    }
}
