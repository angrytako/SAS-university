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

public class IngredientWithDosage {
    private static Map<Integer, IngredientWithDosage> all = new HashMap<>();
    private int id;
    private Ingredient ing;
    private Dosage dosage;

    public IngredientWithDosage(int id, Ingredient ing, Dosage dosage) {
        this.ing = ing;
        this.id = id;
        this.dosage = dosage;
    }

    public static List<IngredientWithDosage> loadIngredentsWithDosage(int cookingItemId) {
        List<IngredientWithDosage> result = new ArrayList();
        String basicIngQuery = "SELECT * from IngredientWithDosage where CookingItem = " + cookingItemId +
                " AND preparationIngredient is NULL;";
        PersistenceManager.executeQuery(basicIngQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    result.add(all.get(id));
                    return;
                }
                ;
                BasicIngredient basicIng = new BasicIngredient(rs.getString("BasicIngredient"));
                int dosage_id = rs.getInt("dosage");
                Dosage dosageResult = null;
                if (dosage_id != 0) {
                    dosageResult = Dosage.loadDosage(dosage_id);
                }
                IngredientWithDosage ing = new IngredientWithDosage(rs.getInt("id"), basicIng,
                        dosageResult);
                result.add(ing);
                all.put(ing.id, ing);
            }
        });
        String prepIngQuery = "SELECT * from IngredientWithDosage where CookingItem = " + cookingItemId +
                " AND BasicIngredient is NULL;";
        PersistenceManager.executeQuery(prepIngQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    result.add(all.get(id));
                    return;
                }
                ;
                Preparation prepIng = Preparation.loadPreparation(rs.getInt("preparationIngredient"));
                int dosage_id = rs.getInt("dosage");
                Dosage dosageResult = null;
                if (dosage_id != 0) {
                    dosageResult = Dosage.loadDosage(dosage_id);
                }
                IngredientWithDosage ing = new IngredientWithDosage(rs.getInt("id"), prepIng,
                        dosageResult);
                result.add(ing);
                all.put(ing.id, ing);
            }
        });
        return result;
    }


    public Ingredient getIng() {
        return ing;
    }

    public Dosage getDosage() {
        return dosage;
    }
}
