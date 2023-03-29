package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;

public class PlayerDeskHandler extends CollisionHandler {
    private final HBox player1HBox = new HBox();
    private final HBox player2HBox = new HBox();
    private final Textbucket bucket;

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
        bucket = new Textbucket();
        player1HBox.setTranslateX(950);
        player1HBox.setTranslateY(25);
        FXGL.runOnce(() -> FXGL.getSceneService().getCurrentScene().addChild(player1HBox), Duration.ZERO);

        player2HBox.setTranslateX(950);
        player2HBox.setTranslateY(380);
        FXGL.runOnce(() -> FXGL.getSceneService().getCurrentScene().addChild(player2HBox), Duration.ZERO);
    }

    public PlayerDeskHandler(EntityType player, EntityType type) {
        super(player, type);
        this.bucket = new Textbucket();
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity desk) {
        int deskNum = desk.getComponent(DeskComponent.class).getDeskNum();
        Text title = new Text(bucket.textMap.get(deskNum));
        title.setWrappingWidth(300);
        title.getStyleClass().add("message");

        if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
            player1HBox.getChildren().clear();
            player1HBox.getChildren().add(title);
        } else {
            player2HBox.getChildren().clear();
            player2HBox.getChildren().add(title);
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        try {
            if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
                player1HBox.getChildren().clear();
            } else {
                player2HBox.getChildren().clear();
            }
        } catch (IllegalArgumentException ignored) {
            // BUGFIX: prevent crash on edge-case when text is shown during level change
        }
    }
}

class Textbucket {
    private String note0 = "Der durchschnittliche Mensch kann mit einem Fahrrad ca. 200 Watt an Strom erzeugen.";

    private String note1 = "Handys verbrauchen weniger Strom als man meint.";

    private String note2 = "Watt ist eine Einheit für Leistung, mit welcher die pro Sekunde verbrauchte "
            + "Energie eines Geräts bezeichnet wird.";

    private String note3 = "Eine Familie verbraucht pro Jahr ca. 4000kwh an Strom";

    private String note4 = "Heutzutage gibt es nur sehr wenige Sachen, die keinen Strom benötigen";

    private String note5 = "In der Stromerzeugung werden erneuerbare Ressourcen wie Wind, Sonne und Wasser immer "
            + "beliebter im Gegensatz zu fossilen Ressourcen wie Holz, Öl und Kohle";

    private String note6 = "Strommasten sind sehr wichtig für das Stromnetz der Schweiz";

    private String note7 = "Unser Gehirn und unser Herz funktionieren wie bei einem Antrieb durch kleine elektrische "
            + "Impulse";

    private String note8 = "Das Treibhausgas CO2 wird hauptsächlich durch Fossile Energien in die Luft verbreitet";

    private String note9 = "Benjamin Franklin hat 1752 mit einem Drachen einen Blitz abgefangen und damit bewiesen, "
            + "dass Blitze elektrisch sind";

    private String note10 = "Das Treibhausgas CO2 wird hauptsächlich durch Fossile Energien in die Luft verbreitet";

    private String note11 = "Strom entsteht durch Bewegung. Kraftwerke nutzen dies aus indem sie durch Ressourcen "
            + "Turbinen antreiben";

    private String note12 = "In der Schweiz sind aktuell 1300 Wasserkraftwerke aktiv und produzieren ca. 57% des "
            + "Stroms. Damit ist die Wasserkraft die wichtigste Stromquelle";

    private String note13 = "Strom besteht aus vielen kleinen Elektronen die wie ein Fluss durch die Haushalte geht";

    private String note14 = "Im Vergleich zu anderen Ländern hat die Schweiz sehr wenige Windkraftanlagen";

    private String note15 = "Metalle und Salzwasser haben die Eigenschaft Strom zu leiten.";

    private String note16 = "Wenn ein Wasserkraftwerk nachts zu viel Strom erzeugt, kann dieser für den "
            + "nächsten Tag gespeichert werden.";

    private String note17 = "Ein Kochherd gehört mit zu den grössten Stromfressern. Deshalb sollte man immer mit "
            + "Deckel kochen, damit sich das Essen schneller erhitzt und Energie gespart werden kann";

    protected HashMap<Integer, String> textMap = new HashMap<>() {
        {
            put(0, note0);
            put(1, note1);
            put(2, note2);
            put(3, note3);
            put(4, note4);
            put(5, note5);
            put(6, note6);
            put(7, note7);
            put(8, note8);
            put(9, note9);
            put(10, note10);
            put(11, note11);
            put(12, note12);
            put(13, note13);
            put(14, note14);
            put(15, note15);
            put(16, note16);
            put(17, note17);
            put(18, "kommt noch");
            put(19, "kommt noch");
        }
    };
}
