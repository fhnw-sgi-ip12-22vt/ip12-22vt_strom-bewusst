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
                Platform.runLater(() -> getSceneService().getCurrentScene().removeChild(messageHBox1));
            } else {
                Platform.runLater(() -> getSceneService().getCurrentScene().removeChild(messageHBox2));
            }
        } catch (IllegalArgumentException ignored) {
            // Bugfix, don't crash when switching rooms while other player is colliding with desk
            // This edge-case is handled in the StromBewusst nextLevel method.
        }
    }
}

class Textbucket {
    private String note0 = "Der Stromverbrauch in einer\n"
            + "Wohnung im Mehrfamilienhaus\n"
            + "ist durchschnittlich fast 30 Prozent\n"
            + "niedriger als im Einfamilienhaus.\n"
            + "Warum? In einem Eigenheim sorgen\n"
            + "beispielsweise Garten und Garage für\n"
            + "höhere Kosten, denn in einem\n"
            + "Mehrfamilienhaus werden diese Punkte\n"
            + "als Nebenkosten auf alle Mieter*innen\n"
            + "umgelegt.";

    private String note1 = "Hochtarif gilt von Montag bis\n"
            + "Freitag von 7 bis 20 Uhr und\n"
            + "am Samstag von 7 bis 13 Uhr. In der\n"
            + "übrigen Zeit gilt Niedertarif. Je nach\n"
            + "Stromprodukt kostet eine Kilowattstunde\n"
            + "im Hochtarif bis zu 22 Rappen und im\n"
            + "Niedertarif nur 16 Rappen. Grund dafür\n"
            + "ist die unterschiedliche Nachfrage\n"
            + "nach Strom.";

    private String note2 = "Watt ist eine Einheit für Leistung,\n"
            + "mit welcher die pro Sekunde verbrauchte\n"
            + "Energie eines Geräts bezeichnet wird.\n"
            + "(ein Watt = 1 Joule pro Sekunde)";

    private String note3 = "Ein Joule ist gleich der Energie, die\n"
            + "benötigt wird, um über die Strecke\n"
            + "von einem Meter die Kraft von einem\n"
            + "Newton aufzuwenden.";

    private String note4 = "Unter Kraft versteht man eine\n"
            + "Einwirkung auf einen Körper.\n"
            + "Die Geschwindigkeit des Körpers\n"
            + "wird vergrössrt.";

    private String note5 = "Ein Haushalt in der Schweiz bezahlt\n"
            + "im Jahr 2022 durchschnittlich 21 Rappen\n"
            + "pro Kilowattstunde.";

    private String note6 = "Ein guter Geschirrspüler verbraucht\n"
            + "zum Spülen von 12 Teller nur etwa\n"
            + "10 Liter Wasser und 0,85 Kilowattstunden\n"
            + "Strom. Beim Spülen von Hand sind\n"
            + "je nach Technik durchschnittlich 70 Liter\n"
            + "notwendig. Hinzu kommen 1,8 kWh\n"
            + "für das Erwärmen des Wassers.";

    private String note7 = "Stromverbrauch von diversen Geräten:\n"
            + "Mikrowelle: ~1300W\n"
            + "Wasserkocher: ~1800W\n"
            + "Toaster: ~800W\n"
            + "Hany laden: ~8W\n"
            + "Haarföhn ~1200W\n"
            + "Laptop laden: ~60W";

    protected HashMap<Integer, String> textMap = new HashMap<>() {
        {
            put(0, note0);
            put(1, "Keine Info momentan");
            put(2, note1);
            put(3, "Keine Info momentan");
            put(4, note2);
            put(5, "Keine Info momentan");
            put(6, note3);
            put(7, "Keine Info momentan");
            put(8, note4);
            put(9, "Keine Info momentan");
            put(10, note5);
            put(11, "Keine Info momentan");
            put(12, note6);
            put(13, "Keine Info momentan");
            put(14, note7); /*
            put(15, "Keine Info momentan");
            put(16, "Keine Info momentan");
            put(17, "Keine Info momentan");*/
        }
    };
}
