package businesslogic.recipe;

import businesslogic.UseCaseLogicException;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicIngredient implements Ingredient {
    public String name;

    public BasicIngredient(String name) {
        this.name = name;
    }

    public static List<BasicIngredient> loadBasicIngredient() {
        List<BasicIngredient> result = new ArrayList();
        String query = "SELECT name FROM BasicIngredient;";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                BasicIngredient ing = new BasicIngredient(rs.getString("name"));
                result.add(ing);
            }
        });
        return result;
    }

    public String getName() {
        return name;
    }
}
