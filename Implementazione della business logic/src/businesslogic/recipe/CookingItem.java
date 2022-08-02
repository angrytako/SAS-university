package businesslogic.recipe;

import java.util.ArrayList;
import java.util.List;

public abstract class CookingItem {
    protected int id;
    protected String name;
    protected String description;
    protected Double concreteTime;
    protected Double totalTime;
    protected Double finishTime;
    protected Boolean isPublished;
    protected List<IngredientWithDosage> ingredients;
    protected List<Step> steps;
    protected Dosage dosage;

    public CookingItem(int id, Boolean isPublished) {
        this.id = id;
        this.isPublished = isPublished;
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }


    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }

    public int getId() {
        return id;
    }

    public List<Preparation> getChildren() {
        List<Preparation> preparations = new ArrayList<>();
        for (IngredientWithDosage ingWD : ingredients) {
            Ingredient ing = ingWD.getIng();
            if (Preparation.class.equals(ing.getClass())) {
                preparations.add((Preparation) ing);
            }
        }
        return preparations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConcreteTime(Double concreteTime) {
        this.concreteTime = concreteTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public void setFinishTime(Double finishTime) {
        this.finishTime = finishTime;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public void addAllIngredientsWithDosage(List<IngredientWithDosage> ingsWDosage) {
        this.ingredients.addAll(ingsWDosage);
    }

    /*
    public static List<CookingItem> loadCookingItem() {
        List<CookingItem> result = new ArrayList();
        String query = "SELECT id,date,location,timeslot" +
                "FROM turns;";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                CookingItem cookingItem = new CookingItem(rs.getInt("id"),rs.getDate("date"),
                        rs.getString("location"),rs.getInt("timeslot"));
                result.add(turn);
            }
        });
        return result;
    }

 */
    @Override
    public String toString() {
        return name;
    }
}
