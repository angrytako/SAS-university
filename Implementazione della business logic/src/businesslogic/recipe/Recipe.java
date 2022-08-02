package businesslogic.recipe;

import businesslogic.UseCaseLogicException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipe extends CookingItem {
    private static Map<Integer, Recipe> all = new HashMap<>();

    public Recipe(int id, Boolean isPublished) {
        super(id, isPublished);
    }


    public static List<Recipe> loadRecipe() {
        List<Recipe> result = new ArrayList();
        String query = "select * from cookingItems where type = 'recipe'";

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                Recipe recipe = new Recipe(rs.getInt("id"), rs.getBoolean("isPublished"));
                if (rs.getString("name") != null) {
                    recipe.setName(rs.getString("name"));
                }
                if (rs.getString("description") != null) {
                    recipe.setDescription(rs.getString("description"));
                }
                if (rs.getObject("concreteTime") != null) {
                    recipe.setConcreteTime(rs.getDouble("concreteTime"));
                }
                if (rs.getObject("totalTime") != null) {
                    recipe.setConcreteTime(rs.getDouble("totalTime"));
                }
                if (rs.getObject("finishTime") != null) {
                    recipe.setConcreteTime(rs.getDouble("finishTime"));
                }

                result.add(recipe);
            }
        });
        return result;
    }

    public static ObservableList<Recipe> loadAllRecipes() {
        String query = "select * from cookingItems where type = 'recipe'";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Recipe rec = all.get(id);
                    rec.name = rs.getString("name");
                } else {
                    Recipe rec = new Recipe(id, rs.getBoolean("isPublished"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });
        ObservableList<Recipe> ret = FXCollections.observableArrayList(all.values());
        Collections.sort(ret, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static ObservableList<Recipe> getAllRecipes() {
        return FXCollections.observableArrayList(all.values());
    }

    public static Recipe loadRecipeById(int id) {
        if (all.containsKey(id)) return all.get(id);
        String query = "select * from cookingItems where type = 'recipe' and id=" + id;
        final Recipe[] rec = new Recipe[1];
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                rec[0] = new Recipe(id, rs.getBoolean("isPublished"));
                rec[0].name = rs.getString("name");
                all.put(rec[0].id, rec[0]);
            }

        });
        List<IngredientWithDosage> ingsWDosage = IngredientWithDosage.loadIngredentsWithDosage(id);
        if (rec[0] != null) {
            rec[0].addAllIngredientsWithDosage(ingsWDosage);
        }

        return rec[0];
    }

    // STATIC METHODS FOR PERSISTENCE

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


}
