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

public class Preparation extends CookingItem implements Ingredient {
    private static Map<Integer, Preparation> all = new HashMap<>();

    public Preparation(int id, Boolean isPublished) {
        super(id, isPublished);
    }


    public static List<Preparation> loadPreparations() {
        List<Preparation> result = new ArrayList();
        String query = "select * from cookingItems where type = 'preparation'";

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                Preparation preparation = new Preparation(rs.getInt("id"), rs.getBoolean("isPublished"));
                if (rs.getString("name") != null) {
                    preparation.setName(rs.getString("name"));
                }
                if (rs.getString("description") != null) {
                    preparation.setDescription(rs.getString("description"));
                }
                if (rs.getObject("concreteTime") != null) {
                    preparation.setConcreteTime(rs.getDouble("concreteTime"));
                }
                if (rs.getObject("totalTime") != null) {
                    preparation.setConcreteTime(rs.getDouble("totalTime"));
                }
                if (rs.getObject("finishTime") != null) {
                    preparation.setConcreteTime(rs.getDouble("finishTime"));
                }

                result.add(preparation);
            }
        });
        return result;
    }

    public static Preparation loadPreparation(int id) {
        if (all.containsKey(id)) return all.get(id);
        String query = "select * from cookingItems where type = 'preparation' and id=" + id;
        final Preparation[] prep = new Preparation[1];
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                prep[0] = new Preparation(id, rs.getBoolean("isPublished"));
                prep[0].name = rs.getString("name");
                all.put(prep[0].id, prep[0]);
            }

        });
        String preparationsQuery = "select * from ingredientwithdosage where CookingItem = " + id;
        List<IngredientWithDosage> ingsWDosage = IngredientWithDosage.loadIngredentsWithDosage(id);
        if (prep[0] != null) {
            prep[0].addAllIngredientsWithDosage(ingsWDosage);
        }
        return prep[0];
    }


}
