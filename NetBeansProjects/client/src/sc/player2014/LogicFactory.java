package sc.player2014;

import sc.player2014.logic.LKLogic;
import sc.player2014.logic.RandomLogic;
import sc.plugin2014.AbstractClient;
import sc.plugin2014.IGameHandler;


/**
 * Erlaubt es verschiedene Logiken zu verwenden und eine davon auszuwählen und
 * Instanzen dieser Logik zu erzeugen
 * 
 * @author and
 */
public enum LogicFactory {
    // Verfügbare Taktiken (Implementierungen des IGameHandler) müssen hier
    // eingetragen wie im Beispiel eingetragen und ihre Klasse angegeben werden
    RANDOM(RandomLogic.class),
    LKLogic(LKLogic.class),
    // Die Logik die gewählt wird, wenn kein passender Eintrag zu der Eingabe
    // gefunden wurde:
    DEFAULT(LKLogic.class);

    private Class<? extends IGameHandler> logic;

    private LogicFactory(Class<? extends IGameHandler> chosenLogic) {
        logic = chosenLogic;
    }

    /**
     * Erstellt eine Logik-Instanz und gibt diese zurück
     * 
     * @param client
     *            Der aktuelle Client
     * @return Eine Instanz der gewaehlten Logik
     * @throws Exception
     *             Wenn etwas schief gelaufen ist und keine Instanz erstellt
     *             werden konnte, wird eine Exception geworfen!
     */
    public IGameHandler getInstance(AbstractClient client) throws Exception {
        System.out.println("Erzeuge Instanz von: " + name());
        return logic.getConstructor(client.getClass()).newInstance(client);
    }

}
