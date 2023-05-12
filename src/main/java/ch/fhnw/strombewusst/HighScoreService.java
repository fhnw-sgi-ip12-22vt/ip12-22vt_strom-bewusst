package ch.fhnw.strombewusst;

import com.almasb.fxgl.core.EngineService;
import com.almasb.fxgl.core.serialization.Bundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * High Score Service class. Originally written for the Geometry Wars Example Game.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class HighScoreService extends EngineService {
    private IntegerProperty score = new SimpleIntegerProperty();

    private ArrayList<HighScoreData> highScores = new ArrayList<>();

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public int getScore() {
        return score.get();
    }

    /**
     * Setter for score. Ideally this is bound to an observable value that tracks the score.
     *
     * @param score the new score
     */
    public void setScore(int score) {
        this.score.set(score);
    }

    /**
     * Remember current score with given tag and reset score to 0.
     *
     * @param tag the team name to attach to this score
     */
    public void commit(String tag) {
        highScores.add(new HighScoreData(tag, getScore()));

        score.unbind();
        setScore(0);

        updateScores();
    }

    /**
     * @return list of high scores sorted in descending order
     */
    public List<HighScoreData> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void updateScores() {
        highScores = highScores.stream().sorted(Comparator.comparingInt(HighScoreData::getScore).reversed())
                .limit(Config.LEADERBOARD_ENTRY_COUNT).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void write(Bundle bundle) {
        bundle.put("highScores", highScores);
    }

    @Override
    public void read(Bundle bundle) {
        highScores = bundle.get("highScores");
    }

    public static final class HighScoreData implements Serializable {
        private static final long serialVersionUID = 1;

        private final String tag;
        private final int score;

        private HighScoreData(String tag, int score) {
            this.tag = tag;
            this.score = score;
        }

        public String getTag() {
            return tag;
        }

        public int getScore() {
            return score;
        }
    }
}
