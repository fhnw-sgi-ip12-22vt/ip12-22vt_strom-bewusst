package ch.fhnw.strombewusst.input;

import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class KeyPressHandler {
    private final List<Runnable> onActionBeginTasks = new ArrayList<>();
    private final List<Runnable> onActionTasks = new ArrayList<>();
    private final List<Runnable> onActionEndTasks = new ArrayList<>();

    public KeyPressHandler(Input input, KeyCode keyCode, String name) {
        input.addAction(new UserAction(name) {
            @Override
            protected void onActionBegin() {
                for (Runnable task : onActionBeginTasks) {
                    task.run();
                }
            }

            @Override
            protected void onAction() {
                for (Runnable task : onActionTasks) {
                    task.run();
                }
            }

            @Override
            protected void onActionEnd() {
                for (Runnable task : onActionEndTasks) {
                    task.run();
                }
            }
        }, keyCode);
    }

    public void onActionBegin(Runnable task) {
        onActionBeginTasks.add(task);
    }
    public void onAction(Runnable task) {
        onActionTasks.add(task);
    }

    public void onActionEnd(Runnable task) {
        onActionEndTasks.add(task);
    }
}
