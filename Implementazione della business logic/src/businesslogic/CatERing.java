package businesslogic;

import businesslogic.event.EventManager;
import businesslogic.event.TurnsTable;
import businesslogic.kitchen.tasks.KitchenTasksManager;
import businesslogic.menu.MenuManager;
import businesslogic.recipe.RecipeManager;
import businesslogic.user.UserManager;
import persistence.KitchenTasksPersistence;
import persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;
    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private MenuPersistence menuPersistence;

    private KitchenTasksPersistence KTPersistence;
    private KitchenTasksManager kTManager;
    private TurnsTable turnsTable;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        turnsTable = new TurnsTable();
        kTManager = new KitchenTasksManager();
        menuPersistence = new MenuPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        KTPersistence = new KitchenTasksPersistence();
        kTManager.addEventReceiver(KTPersistence);
    }

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    public KitchenTasksManager getKTManager() {
        return kTManager;
    }

    public TurnsTable getTurnsTable() {
        return turnsTable;
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

}
