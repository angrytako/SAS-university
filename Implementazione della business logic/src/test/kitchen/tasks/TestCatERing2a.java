package test.kitchen.tasks;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.kitchen.tasks.KitchenTask;
import businesslogic.kitchen.tasks.TaskSummarySheet;

import java.util.List;

public class TestCatERing2a {
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

        KitchenTask firstKitchenTask = tSSheet.getkTasks(0);
        System.out.println("All KitchenTasks created:");
        System.out.println(tSSheet.getKTasks());
        System.out.println("first KitchenTasks:");
        System.out.println(firstKitchenTask);
        System.out.println("position first KitchenTask:");
        System.out.println(tSSheet.getPosition(firstKitchenTask));

        try {
            System.out.println("\n---------------------------------\n\nTEST remove first KitchenTask");
            CatERing.getInstance().getKTManager().removeKitchenTask(tSSheet.getkTasks(0));
        } catch (UseCaseLogicException e) {
            System.out.println("error TEST remove first KitchenTask" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("All KitchenTasks:");
        System.out.println(tSSheet.getKTasks());
        System.out.println("position of the KitchenTask removed (-1 means not found):");
        System.out.println(tSSheet.getPosition(firstKitchenTask));

        try {
            if (tSSheet.getPosition(firstKitchenTask) != -1 && firstKitchenTask != null) throw new Exception();
        } catch (Exception e) {
            System.out.println("error first KitchenTask is the same of last kitchentask" + e.getMessage());
            e.printStackTrace();
        }


    }
}
