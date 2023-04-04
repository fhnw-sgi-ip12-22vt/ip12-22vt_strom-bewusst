package ch.fhnw.strombewusst.rooms;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;

public interface Room {
    Entity getPlayer1();

    Entity getPlayer2();

    Entity getDoor();

    Level getLevel();
    String initText();
}
