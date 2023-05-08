package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.StromBewusst;
import ch.fhnw.strombewusst.TeamName;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;

public class EndGameSubScene extends SubScene {
    private final Button btnEnd;
    private final TeamName teamName = new TeamName();
    private final Label teamNameLabel;

    public EndGameSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Spiel geschafft!");
        title.getStyleClass().add("title");

        Label score = new Label(FXGL.<Score>geto("score").toString());
        score.getStyleClass().add("subtitle");

        teamNameLabel = new Label();
        teamNameLabel.getStyleClass().add("subtitle");
        updateTeamNameLabel();

        VBox titleVBox = new VBox(title, score, teamNameLabel);
        titleVBox.setPrefWidth(getAppWidth());
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setTranslateY(50);

        btnEnd = new Button("Back");
        btnEnd.getStyleClass().add("main_menu_button");
        btnEnd.setStyle("-fx-text-fill: black;");
        btnEnd.setOnAction(e -> ((StromBewusst) FXGL.getApp()).endGame(teamName.getTeamName()));
        HBox endHBox = new HBox(btnEnd);
        endHBox.setPrefWidth(getAppWidth());
        endHBox.setAlignment(Pos.CENTER);
        endHBox.setTranslateY(getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleVBox, endHBox);

        getInput().addAction(new UserAction("update team name first") {
            @Override
            protected void onActionBegin() {
                teamName.updateFirst();
                updateTeamNameLabel();
            }
        }, KeyCode.Q);
        getInput().addAction(new UserAction("update team name second") {
            @Override
            protected void onActionBegin() {
                teamName.updateSecond();
                updateTeamNameLabel();
            }
        }, KeyCode.U);
    }

    /**
     * Updates the username label with a new randomly generated username.
     */
    public void updateTeamNameLabel() {
        teamNameLabel.setText("TeamName: " + teamName.getTeamName());
    }

    @Override
    public void onUpdate(double tpf) {
        if (!(btnEnd.isFocused())) {
            btnEnd.requestFocus();
        }
    }
}

