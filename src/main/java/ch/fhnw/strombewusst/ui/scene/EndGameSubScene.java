package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.StromBewusst;
import ch.fhnw.strombewusst.TeamName;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EndGameSubScene extends SubScene {
    private final Button btnEnd;
    private final TeamName teamName = new TeamName();
    private final Label teamNameLabel;

    public EndGameSubScene() {
        Texture bg = FXGL.getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(Config.WIDTH);
        bg.setFitHeight(Config.HEIGHT);

        Label title = new Label("Spiel geschafft!");
        title.getStyleClass().add("title");

        Label score = new Label(FXGL.<Score>geto("score").toString());
        score.setPadding(new Insets(100, 0, 0, 0));
        score.getStyleClass().add("subtitle");

        teamNameLabel = new Label();
        teamNameLabel.getStyleClass().add("subtitle");
        updateTeamNameLabel();

        VBox titleVBox = new VBox(title, score, teamNameLabel);
        titleVBox.setPrefWidth(Config.WIDTH);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setTranslateY(50);

        Texture selectButton = FXGL.getAssetLoader().loadTexture("red-button-icon-single.png", 68, 68);
        Label selectText = new Label("Zurück zum Hauptmenü");
        selectText.getStyleClass().add("small_title");
        HBox selectHBox = new HBox(selectButton, selectText);
        selectHBox.setAlignment(Pos.CENTER);
        selectHBox.setSpacing(20);

        Texture backButton = FXGL.getAssetLoader().loadTexture("blue-button-icon-single.png", 68, 68);
        Label backText = new Label("Teamnamen ändern");
        backText.getStyleClass().add("small_title");
        HBox backHBox = new HBox(backButton, backText);
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setSpacing(20);

        VBox inputsVBox = new VBox(selectHBox, backHBox);
        inputsVBox.setPrefWidth(Config.WIDTH);
        inputsVBox.setAlignment(Pos.CENTER);
        inputsVBox.setTranslateY(420);

        btnEnd = new Button("Back");
        btnEnd.getStyleClass().add("main_menu_button");
        btnEnd.setStyle("-fx-text-fill: black;");
        btnEnd.setOnAction(e -> ((StromBewusst) FXGL.getApp()).endGame(teamName.getTeamName()));

        HBox endHBox = new HBox(btnEnd);
        endHBox.setPrefWidth(Config.WIDTH);
        endHBox.setAlignment(Pos.CENTER);
        endHBox.setTranslateY(Config.HEIGHT - 140);

        getContentRoot().getChildren().addAll(bg, titleVBox, inputsVBox, endHBox);

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
        teamNameLabel.setText("Teamname: " + teamName.getTeamName());
    }

    @Override
    public void onUpdate(double tpf) {
        if (!(btnEnd.isFocused())) {
            btnEnd.requestFocus();
        }
    }

    public TeamName getTeamName() {
        return teamName;
    }
}

