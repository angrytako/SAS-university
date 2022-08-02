package businesslogic.event;

import businesslogic.UseCaseLogicException;
import businesslogic.kitchen.tasks.KitchenTask;
import businesslogic.user.User;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turn {
    private static Map<Integer, Turn> all = new HashMap<>();
    private int id;
    private Date date;
    private String location;
    private TimeSlot timeSlot;

    private List<KitchenTask> kitchenTasks;
    private List<User> cooks;

    public Turn(int id, Date date, String location, int timeSlot) throws UseCaseLogicException {
        this.id = id;
        this.date = date;
        this.location = location;
        this.timeSlot = new TimeSlot(timeSlot);
        this.kitchenTasks = new ArrayList<>();
        this.cooks = new ArrayList<>();
    }

    public static Turn loadTurn(int turn_id, KitchenTask kTask) {
        if (all.containsKey(turn_id)) {
            Turn turn = all.get(turn_id);
            if (!turn.kitchenTasks.contains(kTask)) {
                turn.kitchenTasks.add(kTask);
            }
            return turn;
        }
        Turn[] turn = new Turn[1];
        String query = "SELECT id,date,location,timeslot" +
                "FROM turns WHERE id =" + turn_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {

                turn[0] = new Turn(turn_id, rs.getDate("date"),
                        rs.getString("location"), rs.getInt("timeslot"));

                all.put(turn[0].id, turn[0]);
                if (!turn[0].kitchenTasks.contains(kTask)) {
                    turn[0].kitchenTasks.add(kTask);
                }
                return;
            }
        });
        return turn[0];
    }

    public static List<Turn> loadAllTurns() {
        List<Turn> result = new ArrayList();
        String query = "SELECT id, date, location, timeslot FROM turns";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    result.add(all.get(id));
                    return;
                }
                Turn turn = new Turn(id, rs.getDate("date"),
                        rs.getString("location"), rs.getInt("timeslot"));
                all.put(id, turn);
                result.add(turn);
            }
        });

        for (Turn turn : result) {
            String queryForCooks = "SELECT * FROM turnscooks WHERE turn = " + turn.id;
            PersistenceManager.executeQuery(queryForCooks, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                    int cook_id = rs.getInt("user");
                    User cook = User.loadUserById(cook_id);
                    if (!turn.cooks.contains(cook)) {
                        turn.cooks.add(cook);
                    }
                }
            });
        }

        for (Turn turn : result) {
            String queryForCooks = "SELECT * FROM kitchentask WHERE turn = " + turn.id;
            PersistenceManager.executeQuery(queryForCooks, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                    int kTask_id = rs.getInt("id");
                    KitchenTask kTask = KitchenTask.loadKitchenTaskById(kTask_id);
                    if (!turn.kitchenTasks.contains(kTask)) {
                        turn.kitchenTasks.add(kTask);
                    }
                }
            });
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void addKitchenTask(KitchenTask task) {
        this.kitchenTasks.add(task);
    }

    public void removeKitchenTask(KitchenTask task) {
        this.kitchenTasks.remove(task);
    }

    public boolean containsCook(User cook) {
        return this.cooks.contains(cook);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "date=" + date +
                ", location='" + location + '\'' +
                ", timeSlot=" + timeSlot +
                ", kitchenTasks=" + kitchenTasks +
                ", cooks=" + cooks +
                '}';
    }

    public List<KitchenTask> getKitchenTasks() {
        return kitchenTasks;
    }

    public List<User> getCooks() {
        return this.cooks;
    }

}
