package test.kitchen.tasks;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.kitchen.tasks.TaskSummarySheet;
import businesslogic.recipe.Dosage;

import java.util.List;

public class TestCatERing2b {
    public static void main(String[] args) {
        System.out.println("TEST FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Marinella");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
        System.out.println("\nLOADING EVENT");
        List<EventInfo> events = EventInfo.loadAllEventInfo();
        EventInfo event = events.get(0);
        System.out.println("---------------------------------\nFirst Event Info:");
        System.out.println(event);
        System.out.println("First Event Services:");
        System.out.println(event.getServices());
        System.out.println("First Event owner:");
        System.out.println(event.getOwner());
        System.out.println("Current user:");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
        System.out.println("First Service menu:");
        System.out.println(event.getServices().get(0).getMenu());

        System.out.println("First Service menu recipes:");
        System.out.println(event.getServices().get(0).getMenu().getRecipes());

        System.out.println("First Recicpe Preparations:");
        System.out.println(event.getServices().get(0).getMenu().getRecipes());
        System.out.println(event.getServices().get(0).getMenu().getRecipes().get(0).getChildren());


        TaskSummarySheet tSSheet = null;
        try {
            System.out.println("\n---------------------------------\n\nCreate task summary sheet from first event:");
            tSSheet = CatERing.getInstance().getKTManager().createTaskSummarySheet(event.getServices().get(0));
        } catch (UseCaseLogicException e) {
            System.out.println("task summary sheet created error" + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("All KitchenTasks created:");
        System.out.println(tSSheet.getKTasks());


        try {
            System.out.println("\n---------------------------------\n\nTEST divideKitchenTask first");
            CatERing.getInstance().getKTManager().divideKitchenTask(tSSheet.getkTasks(0), Dosage.loadDosage(1), Dosage.loadDosage(1));
        } catch (UseCaseLogicException e) {
            e.printStackTrace();
        }

        System.out.println("All KitchenTasks (the clone task of the first task is in last position):");
        System.out.println(tSSheet.getKTasks());


    }
}
