package businesslogic.kitchen.tasks;

import businesslogic.UseCaseLogicException;
import businesslogic.event.Turn;
import businesslogic.recipe.CookingItem;
import businesslogic.recipe.Dosage;
import businesslogic.recipe.Preparation;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class KitchenTask {
    private static Map<Integer, KitchenTask> all = new HashMap<>();
    int id;
    private CookingItem cItem;
    private Boolean isComplete;
    private long estimatedTime;
    private Turn kTurn = null;   // non serve ma ci aiuta a ricordare che

    private Dosage kDosage;
    private User kCook;

    public KitchenTask(CookingItem cItem, int id, Dosage dosage, Boolean isComplete, User cook, Long estimatedTime) {
        this.cItem = cItem;
        this.id = id;
        this.kDosage = dosage;
        this.isComplete = isComplete;
        this.kCook = cook;
        this.estimatedTime = estimatedTime;
    }

    public KitchenTask(CookingItem cItem) {
        this.cItem = cItem;
        if (cItem.getDosage() != null) {
            this.kDosage = cItem.getDosage().clone();
        }
        this.isComplete = false;
    }

    public static List<KitchenTask> getAllTasksFromCookingItem(CookingItem item) {
        List<KitchenTask> kTasks = new ArrayList<>();
        Queue<CookingItem> queue = new LinkedList<>();
        queue.add(item);
        while (!queue.isEmpty()) {
            CookingItem cItem = queue.remove();
            KitchenTask kTask = new KitchenTask(cItem);
            kTasks.add(kTask);
            queue.addAll(cItem.getChildren());
        }
        return kTasks;
    }

    public static void deleteKitchenTask(KitchenTask item) {
        String delKT = "DELETE FROM kitchentask WHERE id = " + item.id;
        PersistenceManager.executeUpdate(delKT);
        all.remove(item.id);
    }

    public static void saveSpecification(KitchenTask kTask) {
        String upd;
        if (kTask.kDosage != null) {
            if (kTask.kDosage.getId() == 0) {
                Dosage.saveDosage(kTask.kDosage);
            }
            upd = "UPDATE kitchentask SET dosage = " + kTask.kDosage.getId() + ", estimatedTime= " + kTask.estimatedTime +
                    " WHERE id = " + kTask.id;
        } else {
            upd = "UPDATE kitchentask SET dosage = NULL , estimatedTime= " + kTask.estimatedTime +
                    " WHERE id = " + kTask.id;
        }
        PersistenceManager.executeUpdate(upd);
    }

    public static void savePosition(KitchenTask kTask, int position) {
        String upd = "UPDATE kitchentask SET position = " + position +
                " WHERE id = " + kTask.id;
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveCompleted(KitchenTask kTask) {
        String upd = "UPDATE kitchentask SET isComplete = " + (kTask.isComplete ? 1 : 0) +
                " WHERE id = " + kTask.id;
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveAssignment(KitchenTask kTask) {
        String cookString;
        if (kTask.kCook == null) {
            cookString = " cook = NULL";
        } else {
            cookString = " cook = " + kTask.kCook.getId();
        }

        String turnString;
        if (kTask.kTurn == null) {
            turnString = ", turn = NULL";
        } else {
            turnString = ", turn = " + kTask.kTurn.getId();
        }

        String upd = "UPDATE kitchentask SET" + cookString + turnString +
                " WHERE id = " + kTask.id;
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveAllNewKitchenTasks(List<KitchenTask> kitchenTasks, int serviceId, int startingPosition) {
        String taskInsert = "INSERT INTO catering.kitchentask (TaskSummarySheet, CookingItem, isComplete, dosage, position) VALUES ( ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(taskInsert, kitchenTasks.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {

                ps.setInt(1, serviceId);
                ps.setInt(2, kitchenTasks.get(batchCount).cItem.getId());
                ps.setBoolean(3, kitchenTasks.get(batchCount).isComplete);
                if (kitchenTasks.get(batchCount).kDosage == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, kitchenTasks.get(batchCount).kDosage.getId());
                }
                ps.setInt(5, batchCount + startingPosition);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                kitchenTasks.get(count).id = rs.getInt(1);
            }
        });
    }

    public static void saveNewKitchenTask(KitchenTask kitchenTask, int serviceId, int position) {
        String taskInsert = "INSERT INTO catering.kitchentask (TaskSummarySheet, CookingItem, isComplete, dosage, position) VALUES ( ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(taskInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {

                ps.setInt(1, serviceId);
                ps.setInt(2, kitchenTask.cItem.getId());
                ps.setBoolean(3, kitchenTask.isComplete);
                if (kitchenTask.kDosage == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, kitchenTask.kDosage.getId());
                }
                ps.setInt(5, position);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    kitchenTask.id = rs.getInt(1);
                }
            }
        });
    }

    public static List<KitchenTask> loadKitchenTasks(int tSSheet_id) {
        List<KitchenTask> result = new ArrayList();
        String query = "SELECT * FROM KitchenTask where TaskSummarySheet = " + tSSheet_id +
                " ORDER BY position";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    result.add(all.get(id));
                    return;
                }
                CookingItem cItem = null;
                cItem = Recipe.loadRecipeById(rs.getInt("CookingItem"));
                if (cItem == null) {
                    cItem = Preparation.loadPreparation(rs.getInt("CookingItem"));
                }
                int dosage_id = rs.getInt("dosage");
                Dosage dosage = null;
                if (dosage_id != 0) {
                    dosage = Dosage.loadDosage(dosage_id);
                }

                int cook_id = rs.getInt("dosage");
                User cook = null;
                if (cook_id != 0) {
                    cook = User.loadUserById(cook_id);
                }
                KitchenTask kitchenTask = new KitchenTask(cItem, id, dosage,
                        rs.getBoolean("isComplete"), cook, rs.getLong("estimatedTime"));

                int turn_id = rs.getInt("turn");
                Turn turn = null;
                if (turn_id != 0) {
                    turn = Turn.loadTurn(turn_id, kitchenTask);
                    kitchenTask.setKTurn(turn);
                }

                all.put(id, kitchenTask);
                result.add(kitchenTask);
            }
        });
        return result;
    }

    public static KitchenTask loadKitchenTaskById(int kTask_id) {
        if (all.containsKey(kTask_id)) {
            return all.get(kTask_id);
        }
        KitchenTask[] result = new KitchenTask[1];
        String query = "SELECT * FROM KitchenTask where id = " + kTask_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {

                CookingItem cItem = null;
                cItem = Recipe.loadRecipeById(rs.getInt("CookingItem"));
                if (cItem == null) {
                    cItem = Preparation.loadPreparation(rs.getInt("CookingItem"));
                }
                int dosage_id = rs.getInt("dosage");
                Dosage dosage = null;
                if (dosage_id != 0) {
                    dosage = Dosage.loadDosage(dosage_id);
                }

                int cook_id = rs.getInt("dosage");
                User cook = null;
                if (cook_id != 0) {
                    cook = User.loadUserById(cook_id);
                }
                KitchenTask kitchenTask = new KitchenTask(cItem, kTask_id, dosage,
                        rs.getBoolean("isComplete"), cook, rs.getLong("estimatedTime"));
                all.put(kTask_id, kitchenTask);
                int turn_id = rs.getInt("turn");
                Turn turn = null;
                if (turn_id != 0) {
                    turn = Turn.loadTurn(turn_id, kitchenTask);
                    kitchenTask.setKTurn(turn);
                }


                result[0] = kitchenTask;
            }
        });
        return result[0];
    }

    public Turn getkTurn() {
        return kTurn;
    }

    public User getkCook() {
        return kCook;
    }

    public CookingItem getCItem() {
        return this.cItem;
    }

    public void setDosage(Dosage dosage) {
        this.kDosage = dosage;
    }

    public void assignTurn(Turn turn) throws UseCaseLogicException {
        if (kTurn != null) throw new UseCaseLogicException("Kitchen task has already a turn assigned");
        this.kTurn = turn;
        turn.addKitchenTask(this);

    }

    public void editTurn(Turn turn) {
        this.kTurn.removeKitchenTask(this);
        this.kTurn = turn;
        if (this.kTurn != null) {
            this.kTurn.addKitchenTask(this);
        }
    }

    public void setCook(User cook) {
        this.kCook = cook;
    }

    private void setKTurn(Turn turn) {
        this.kTurn = turn;
    }

    public void complete() {
        this.isComplete = true;
    }

    public void setEstimatedTime(long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return "KitchenTask{" +
                "cItem=" + cItem + "}";
    }

    public String toCompleteString() {
        return "KitchenTask{" +
                "cItem=" + cItem +
                ", isComplete=" + isComplete +
                ", estimatedTime=" + estimatedTime +
                ", kTurn=" + kTurn +
                ", kDosage=" + kDosage +
                ", kCook=" + kCook +
                '}';
    }

    public Boolean isCompleted() {
        return isComplete;
    }
}
