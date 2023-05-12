package ch.fhnw.strombewusst;

import com.almasb.fxgl.dsl.FXGL;

import java.util.List;
import java.util.Random;

public class TeamName {
    private final List<String> nameCandidatesFirst = FXGL.getAssetLoader().loadText(Config.TEAM_NAMES_FIRST_LIST_PATH);
    private final List<String> nameCandidatesSecond = FXGL.getAssetLoader().loadText(Config.TEAM_NAMES_SECOND_LIST_PATH);

    private String nameFirst;
    private String nameSecond;

    private final Random random = new Random();

    public TeamName() {
        nameFirst = nameCandidatesFirst.get(random.nextInt(nameCandidatesFirst.size()));
        nameSecond = nameCandidatesSecond.get(random.nextInt(nameCandidatesSecond.size()));
    }

    /**
     * Updates the first part of the team name, by randomly choosing a different word from the word list.
     */
    public void updateFirst() {
        int i = random.nextInt(nameCandidatesFirst.size());
        while (nameFirst.equals(nameCandidatesFirst.get(i))) {
            i = random.nextInt(nameCandidatesFirst.size());
        }

        nameFirst = nameCandidatesFirst.get(i);
    }

    /**
     * Updates the second part of the team name, by randomly choosing a different word from the word list.
     */
    public void updateSecond() {
        int i = random.nextInt(nameCandidatesSecond.size());
        while (nameSecond.equals(nameCandidatesSecond.get(i))) {
            i = random.nextInt(nameCandidatesSecond.size());
        }

        nameSecond = nameCandidatesSecond.get(i);
    }

    public String getTeamName() {
        return nameFirst + " " + nameSecond;
    }
}
