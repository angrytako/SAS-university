package businesslogic.recipe;

import businesslogic.UseCaseLogicException;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dosage {
    private static Map<Integer, Dosage> all = new HashMap<>();
    private int id;
    private Double quantity;
    private Label label;

    public Dosage(int id, Double quantity, Label label) {
        this.id = id;
        this.quantity = quantity;
        this.label = label;
    }

    public Dosage(Double quantity, Label label) {
        this.id = 0;
        this.quantity = quantity;
        this.label = label;
    }

    public static void saveDosage(Dosage kDosage) {
        String saveDosage = "INSERT INTO  dosages (quantity, label) VALUES(" + kDosage.quantity + ", '" +
                kDosage.label.getLabel() + "' );";
        PersistenceManager.executeUpdate(saveDosage);
    }

    public static Dosage loadDosage(int dosage_id) {
        if (all.containsKey(dosage_id)) return all.get(dosage_id);
        Dosage[] result = new Dosage[1];
        String query = "SELECT id,quantity,label FROM dosages where id = " + dosage_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {

                result[0] = new Dosage(rs.getInt("id"), rs.getDouble("quantity"),
                        new Label(rs.getString("label")));
            }
        });
        all.put(result[0].id, result[0]);
        return result[0];
    }

    public static List<Dosage> loadTurn() {
        List<Dosage> result = new ArrayList();
        String query = "SELECT id,quantity,label FROM dosages";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {

                Dosage dosage = new Dosage(rs.getInt("id"), rs.getDouble("quantity"),
                        new Label(rs.getString("label")));
                result.add(dosage);
            }
        });
        return result;
    }

    public Dosage clone() {
        return new Dosage(this.id, this.quantity, this.label);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Dosage{" +
                "quantity=" + quantity +
                ", label=" + label +
                '}';
    }
}
