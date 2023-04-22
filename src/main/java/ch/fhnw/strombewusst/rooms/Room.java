package ch.fhnw.strombewusst.rooms;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;

public interface Room {
    /**
     * Returns the player 1 entity.
     * @return the player 1 entity.
     */
    Entity getPlayer1();

    /**
     * Returns the player 2 entity.
     * @return the player 2 entity.
     */
    Entity getPlayer2();

    /**
     * Returns the door entity.
     * @return the door entity.
     */
    Entity getDoor();

    /**
     * Returns the game level containing all entities.
     * @return the game level containing all entities.
     */
    Level getLevel();
}
