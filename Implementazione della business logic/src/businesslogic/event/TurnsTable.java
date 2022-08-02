package businesslogic.event;

import java.util.List;


public class TurnsTable {
    public List<Turn> turns;

    public TurnsTable() {
        loadTurns();
    }

    public List<Turn> getAllTurns() {
        return turns;
    }


    public void loadTurns() {
        turns = Turn.loadAllTurns();
    }
}
