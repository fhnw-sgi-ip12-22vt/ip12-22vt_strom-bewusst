package ch.fhnw.strombewusst.collision;

import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class PlayerDeskHandler extends CollisionHandler {

    private HBox messageHBox1, messageHBox2;
    private final Textbucket bucket;

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
        bucket = new Textbucket();
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity desk) {
        int deskNum = desk.getComponent(DeskComponent.class).getDeskNum();
        Text title = new Text(bucket.textMap.get(deskNum));
        title.getStyleClass().add("message");

        if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
            messageHBox1 = new HBox(title);
            messageHBox1.setTranslateX(950);
            messageHBox1.setTranslateY(25);
            getSceneService().getCurrentScene().addChild(messageHBox1);
        } else {
            messageHBox2 = new HBox(title);
            messageHBox2.setTranslateX(950);
            messageHBox2.setTranslateY(380);
            getSceneService().getCurrentScene().addChild(messageHBox2);
        }
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        try {
            if (player.getComponent(PlayerComponent.class).getPlayerNum() == 1) {
                getSceneService().getCurrentScene().removeChild(messageHBox1);
            } else {
                getSceneService().getCurrentScene().removeChild(messageHBox2);
            }
        } catch (IllegalArgumentException ignored) {
            // Bugfix, don't crash when switching rooms while other player is
            // colliding with desk
            // TODO: fully remove the textbox in this edge case
        }
    }
}

class Textbucket {
    private String note0 =  "Der durchschnittliche Mensch \n" +
                            " kannmit einem Fahrrad ca. 200 Watt an \n" +
                            "Strom erzeugen.";

    private String note1 =  "Handys verbrauchen nicht so viel Strom \n" +
                            "wie man meint.";

    private String note2 =  "Watt ist eine Einheit für Leistung,\n"
            +               "mit welcher die pro Sekunde verbrauchte\n"
            +               "Energie eines Geräts bezeichnet wird.\n";

    private String note3 =  "Eine Familie verbraucht pro Jahr  \n " +
                            "ca 4000kwh an Strom";

    private String note4 =  "Heutzutage gibt es nur sehr \n " +
                            "wenige Sachen,die kein Strom  \n benötigen";

    private String note5 =  "In der Strom Erzeugung werden \n " +
                            "Erneuerbare Ressourcen wie Wind, \n" +
                            "Sonne und Wasser immer beliebter im \n" +
                            "Gegensatz zu Fossilen Ressourcen wie \n" +
                            "Holz, Öl und Kohle";

    private String note6 =  "Stromäste sind sehr wichtig \n" +
                            "für das Stromnetz der Schweiz";

    private String note7 =  "Unser Gehirn und unser \n" +
                            "Herz funktionieren wie bei einem Antrieb \n " +
                            "durch kleine elektrische Impulse";

    private String note8 =  "Das Treibhausgas CO2 wird \n" +
                            "hauptsächlich durch Fossile Energien \n" +
                            "in die Luft verbreitet";

    private String note9 =  "Benjamin Franklin hat 1752 mit \n " +
                            "einem Drachen einen Blitz abgefangen \n " +
                            "und damit bewiesen das Blitze elektrisch sind.";

    private String note10 = "Das Treibhausgas CO2 wird \n" +
                            "hauptsächlich durch Fossile \n " +
                            "Energien in die Luft verbreitet";

    private String note11 = "Strom entsteht durch Bewegung. \n " +
                            "Kraftwerke utzen dies aus indem sie \n " +
                            "durch Ressourcen Turbinen antreiben";

    private String note12 = "In der Schweiz sind aktuell 1300 \n " +
                            "Wasserkraftwerke aktiv und produzieren ca.\n" +
                            "57% des Stroms. Damit ist die Wasserkraft die \n" +
                            "wichtigste Stromquelle";

    private String note13 = "Strom besteht aus vielen kleinen Elektronen \n" +
                            "die wie ein Fluss durch die Haushalte geht";

    private String note14 = "Im Vergleich zu anderen Ländern \n" +
                            "hat die Schweiz sehr wenige Windkraftanlagen";

    private String note15 = "Metalle und Salzwasser haben \n" +
                            "die Eigenschaft Strom zu leiten.";

    private String note16 = "Wenn ein Wasserkraftwerk \n" +
                            "nachts zu viel Strom erzeugen sollte, kann \n" +
                            "dieser genutzt werden, um neuen Strom für \n" +
                            "den nächsten Tag zu erzeugen. ";

    private String note17 = "Ein Kochherd gehört mit \n" +
                            "zu den grössten Stromfressern. Deshalb  \n" +
                            "sollte man immer mir Deckel kochen damit \n" +
                            "sich das Essen schneller erhitzt und Energie \n" +
                            "gespart werden kann";

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
        }
    };
}
