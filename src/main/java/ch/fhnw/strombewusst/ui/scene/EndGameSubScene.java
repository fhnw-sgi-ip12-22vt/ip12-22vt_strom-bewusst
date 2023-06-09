package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Config;
import ch.fhnw.strombewusst.InputHandler;
import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.TeamName;
import ch.fhnw.strombewusst.Timer;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Defines the layout of our end game sub-scene.
 */
public class EndGameSubScene extends SubScene {
    private final TeamName teamName = new TeamName();
    private final Label teamNameLabel;

    public EndGameSubScene() {
        Texture bg = FXGL.getAssetLoader().loadTexture("background/menu-ohne-logo.png");
        bg.setFitWidth(Config.WIDTH);
        bg.setFitHeight(Config.HEIGHT);

        Label title = new Label("Spiel geschafft!");
        title.getStyleClass().add("title");

        Score score = FXGL.geto("score");
        int actualTime = Config.TIMER_INITIAL_SECONDS - FXGL.<Timer>geto("timer").getSecondsRemainingProperty().get();
        int bonus = score.addBonusPoints(Config.TIMER_INITIAL_SECONDS, actualTime);

        Label scoreLabel = new Label(score.toString());
        scoreLabel.getStyleClass().add("subtitle");

        Label bonusLabel = new Label("(" + bonus + " BONUS)");
        bonusLabel.getStyleClass().addAll("subtitle", "bonus");

        HBox scoreHBox = new HBox(scoreLabel, bonusLabel);
        scoreHBox.setSpacing(10);
        scoreHBox.setPadding(new Insets(100, 0, 0, 0));
        scoreHBox.setAlignment(Pos.CENTER);

        teamNameLabel = new Label();
        teamNameLabel.getStyleClass().add("subtitle");
        updateTeamNameLabel();

        VBox titleVBox = new VBox(title, scoreHBox, teamNameLabel);
        titleVBox.setPrefWidth(Config.WIDTH);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setTranslateY(150);

        Texture selectButton = FXGL.getAssetLoader().loadTexture("red-button-icon-single.png", 51, 51);
        Button selectText = new Button("Zurück zum Hauptmenü");
        selectText.getStyleClass().add("main_menu_button");
        Platform.runLater(selectText::requestFocus);
        selectText.setOnAction((e) -> InputHandler.handleSelect(null));
        HBox selectHBox = new HBox(selectButton, selectText);
        selectHBox.setAlignment(Pos.CENTER);
        selectHBox.setSpacing(20);

        Texture backButton = FXGL.getAssetLoader().loadTexture("blue-button-icon-single.png", 51, 51);
        Label backText = new Label("Teamnamen ändern");
        backText.getStyleClass().add("main_menu_button");
        HBox backHBox = new HBox(backButton, backText);
        backHBox.setAlignment(Pos.CENTER);
        backHBox.setSpacing(20);

        VBox inputsVBox = new VBox(selectHBox, backHBox);
        inputsVBox.setPrefWidth(Config.WIDTH);
        inputsVBox.setAlignment(Pos.CENTER);
        inputsVBox.setTranslateY(520);

        getContentRoot().getChildren().addAll(bg, titleVBox, inputsVBox);

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
        getInput().addAction(new UserAction("end game") {
            @Override
            protected void onActionBegin() {
                InputHandler.handleSelect(null);
            }
        }, KeyCode.ESCAPE);
    }

    /**
     * Updates the username label with a new randomly generated username.
     */
    public void updateTeamNameLabel() {
        teamNameLabel.setText("Teamname: " + teamName.toString());
    }

    public TeamName getTeamName() {
        return teamName;
    }
}

