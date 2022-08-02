package businesslogic.event;

import businesslogic.kitchen.tasks.TaskSummarySheet;
import businesslogic.menu.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private boolean approvedMenu;

    private EventInfo event;
    private Menu menu;
    private TaskSummarySheet tSSheet;

    public ServiceInfo(String name) {
        this.name = name;

    }

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(EventInfo event) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT * FROM Services WHERE event_id = " + event.getId();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");

                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.event = event;
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                int proposed_menu_id = rs.getInt("proposed_menu_id");
                int approved_menu_id = rs.getInt("approved_menu_id");
                serv.approvedMenu = approved_menu_id != 0;
                serv.menu = Menu.loadMenu(proposed_menu_id != 0 ? proposed_menu_id : approved_menu_id);
                serv.tSSheet = TaskSummarySheet.loadSSheetsByEventID(serv.id);
                result.add(serv);
            }
        });

        return result;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    public EventInfo getEvent() {
        return this.event;
    }

    public Menu getMenu() {
        return this.menu;
    }

    // STATIC METHODS FOR PERSISTENCE

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public TaskSummarySheet getTSSheet() {
        return tSSheet;
    }

    public void setTSSheet(TaskSummarySheet taskSummarySheet) {
        this.tSSheet = taskSummarySheet;
    }

    public boolean isMenuApproved() {
        return this.approvedMenu;
    }

    public int getId() {
        return id;
    }


}
