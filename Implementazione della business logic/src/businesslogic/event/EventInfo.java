package businesslogic.event;

import businesslogic.menu.Menu;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    private User organizer;
    private User chef;
    private Boolean menusApproved;

    private ObservableList<ServiceInfo> services;
    private ObservableList<Menu> menus;

    public EventInfo(String name) {
        this.name = name;
        id = 0;
    }

    public static ObservableList<EventInfo> loadAllEventInfo() {
        ObservableList<EventInfo> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                EventInfo e = new EventInfo(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                int org = rs.getInt("organizer_id");
                int chef = rs.getInt("chef_id");
                e.organizer = User.loadUserById(org);
                e.chef = User.loadUserById(chef);
                all.add(e);
            }
        });

        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e);
        }
        return all;
    }

    public int getId() {
        return id;
    }

    public ObservableList<ServiceInfo> getServices() {
        return FXCollections.unmodifiableObservableList(this.services);
    }


    public Boolean isOwner(User user) {
        return chef.equals(user);
    }

    // STATIC METHODS FOR PERSISTENCE

    public String toString() {
        return name + ": " + dateStart + "-" + dateEnd + ", " + participants + " pp. (" + organizer.getUserName() + ")";
    }

    public boolean hasMenu(Menu menu) {
        for (Menu m : this.menus) {
            if (m.equals(menu)) return true;
        }
        return false;
    }

    public User getOwner() {
        return this.chef;
    }
}
