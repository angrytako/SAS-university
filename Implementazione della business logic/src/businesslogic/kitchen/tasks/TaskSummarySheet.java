package businesslogic.kitchen.tasks;

import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.event.Turn;
import businesslogic.menu.Menu;
import businesslogic.recipe.CookingItem;
import businesslogic.recipe.Dosage;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskSummarySheet {
    private static Map<Integer, TaskSummarySheet> all = new HashMap<>();
    private List<KitchenTask> kTasks;
    private int id;

    public TaskSummarySheet(ServiceInfo service) {
        this.kTasks = new ArrayList<>();
        Menu menu = service.getMenu();
        for (Recipe recipe : menu.getRecipes()) {
            this.kTasks.addAll(KitchenTask.getAllTasksFromCookingItem(recipe));
        }
        service.setTSSheet(this);
    }

    public TaskSummarySheet(int id) {
        this.id = id;
    }

    public static TaskSummarySheet getTSSheet(int service_id) {
        return all.get(service_id);
    }

    public static void loadAllTSSheets() {
        String allTSSheetsQuery = "SELECT * from tasksummarysheet;";
        List<TaskSummarySheet> tSSheets = new ArrayList<>();
        PersistenceManager.executeQuery(allTSSheetsQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                int id = rs.getInt("service");
                if (!all.containsKey(id)) {
                    TaskSummarySheet tSSheet = new TaskSummarySheet(id);
                    tSSheets.add(tSSheet);
                    all.put(id, tSSheet);
                }
            }
        });
        for (TaskSummarySheet tSSheet : tSSheets) {
            tSSheet.setKTasks(KitchenTask.loadKitchenTasks(tSSheet.id));
        }

    }

    public static TaskSummarySheet loadSSheetsByEventID(int eventID) {
        if (all.containsKey(eventID)) {
            return all.get(eventID);
        }
        String allTSSheetsQuery = "SELECT * from tasksummarysheet WHERE service=" + eventID + ";";
        TaskSummarySheet[] tSSheets = new TaskSummarySheet[1];
        PersistenceManager.executeQuery(allTSSheetsQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                    TaskSummarySheet tSSheet = new TaskSummarySheet(eventID);
                    tSSheets[0] = tSSheet;
                    all.put(eventID, tSSheet);

            }
        });
        if (tSSheets[0] != null) {
            tSSheets[0].setKTasks(KitchenTask.loadKitchenTasks(tSSheets[0].id));
        }
        return tSSheets[0];
    }

    public static void saveNewTaskSummarySheet(TaskSummarySheet tSSheet, ServiceInfo service) {
        String taskInsert = "INSERT INTO catering.tasksummarysheet (service) VALUES (?);";
        int[] result = PersistenceManager.executeBatchUpdate(taskInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, service.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    tSSheet.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {

            // salva le voci libere
            if (tSSheet.kTasks.size() > 0) {
                KitchenTask.saveAllNewKitchenTasks(tSSheet.kTasks, service.getId(), 0);
            }
        }
    }

    public List<KitchenTask> addCookingItem(CookingItem item) {
        List<KitchenTask> newKTask = KitchenTask.getAllTasksFromCookingItem(item);
        this.kTasks.addAll(newKTask);
        return newKTask;
    }

    public boolean hasKitchenTask(KitchenTask item) {
        return this.kTasks.contains(item);
    }

    public void removeKitchenTask(KitchenTask item) {
        this.kTasks.remove(item);
        if(item.getkTurn()!=null){
            item.getkTurn().removeKitchenTask(item);
        }
    }

    public void addTask(KitchenTask copyTask) {
        this.kTasks.add(copyTask);
    }

    public void moveKitchenTask(KitchenTask kTask, int position) throws UseCaseLogicException {
        if (position < 0 && position >= this.kTasks.size()) {
            throw new UseCaseLogicException();
        }
        this.kTasks.remove(kTask);
        this.kTasks.add(position, kTask);
    }

    public KitchenTask assignsTask(KitchenTask task, Turn turn, User cook) throws UseCaseLogicException {

        if (cook != null) {
            if (!turn.containsCook(cook)) {
                throw new UseCaseLogicException("the cook is not available on turn");
            }
            task.setCook(cook);
        }

        task.assignTurn(turn);

        return task;
    }

    public KitchenTask editAssignment(KitchenTask task, Turn turn, User cook) throws UseCaseLogicException {

        if (cook != null && !turn.containsCook(cook)) {
            throw new UseCaseLogicException("the cook is not available on turn");
        }
        task.setCook(cook);
        task.editTurn(turn);

        return task;
    }

    public void taskCompleted(KitchenTask task) throws UseCaseLogicException {
        task.complete();
    }

    public int getPosition(KitchenTask kTask) {
        int i = 0;
        for (KitchenTask task : this.kTasks) {
            if (task.equals(kTask)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public KitchenTask setAssignmentSpecifications(KitchenTask task, long estimatedTime, Dosage dosage) throws UseCaseLogicException {
        task.setEstimatedTime(estimatedTime);
        task.setDosage(dosage);
        return task;
    }

    public int getId() {
        return id;
    }

    public List<KitchenTask> getKTasks() {
        return this.kTasks;
    }

    private void setKTasks(List<KitchenTask> kTasks) {
        this.kTasks = kTasks;
    }

    public List<KitchenTask> getAllTasksFromPosition(int position) {
        List<KitchenTask> kTasks = new ArrayList<>();
        for (KitchenTask kTask : this.kTasks) {
            kTasks.add(this.kTasks.get(position));
            position++;
        }
        return kTasks;
    }


    public KitchenTask getkTasks(int position) {
        return kTasks.get(position);
    }
}