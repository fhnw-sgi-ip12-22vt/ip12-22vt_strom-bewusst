package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.Score;
import ch.fhnw.strombewusst.Username;
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
import static com.almasb.fxgl.dsl.FXGL.getGameController;

public class EndGameSubScene extends SubScene {
    private final Button btnEnd;
    private final Username username = new Username();
    private final Label usernameLabel;

    public EndGameSubScene() {
        Texture bg = getAssetLoader().loadTexture("background/mainmenu.png");
        bg.setFitWidth(getAppWidth());
        bg.setFitHeight(getAppHeight());

        Label title = new Label("Spiel geschafft!");
        title.getStyleClass().add("title");

        Label score = new Label(FXGL.<Score>geto("score").toString());
        score.getStyleClass().add("subtitle");

        usernameLabel = new Label();
        usernameLabel.getStyleClass().add("subtitle");
        newUsername();

        VBox titleVBox = new VBox(title, score, usernameLabel);
        titleVBox.setPrefWidth(getAppWidth());
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setTranslateY(50);

        btnEnd = new Button("Back");
        btnEnd.getStyleClass().add("main_menu_button");
        btnEnd.setStyle("-fx-text-fill: black;");
        btnEnd.setOnAction(e -> getGameController().gotoMainMenu());
        HBox endHBox = new HBox(btnEnd);
        endHBox.setPrefWidth(getAppWidth());
        endHBox.setAlignment(Pos.CENTER);
        endHBox.setTranslateY(getAppHeight() - 140);

        getContentRoot().getChildren().addAll(bg, titleVBox, endHBox);

        getInput().addAction(new UserAction("checkAnswers") {
            @Override
            protected void onActionBegin() {
                newUsername();
            }
        }, KeyCode.Q);
    }

    public void newUsername() {
        usernameLabel.setText("Username: " + username.getNewUsername());
    }

    @Override
    public void onUpdate(double tpf) {
        if (!(btnEnd.isFocused())) {
            btnEnd.requestFocus();
        }
    }
}

