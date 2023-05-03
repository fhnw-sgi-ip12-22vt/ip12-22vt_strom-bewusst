package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;

import java.util.List;
import java.util.Random;

public class Username {
    private final List<String> usernames = FXGL.getAssetLoader().loadText("usernames.txt");
    private final Random random = new Random();

    public String getNewUsername() {
        return usernames.get(random.nextInt(usernames.size()));
    }
}
