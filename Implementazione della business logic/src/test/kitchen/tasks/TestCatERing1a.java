package test.kitchen.tasks;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.kitchen.tasks.TaskSummarySheet;

import java.util.List;

public class TestCatERing1a {
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
        System.out.println("\n---------------------------------\n\nCreate task summary sheet from first event:");
        try {
            tSSheet = CatERing.getInstance().getKTManager().createTaskSummarySheet(event.getServices().get(0));
            System.out.println("Task summary sheet created:" + event.getServices().get(0).getTSSheet());
        } catch (UseCaseLogicException e) {
            System.out.println("task summary sheet created error" + e.getMessage());
            e.printStackTrace();
        }


        TaskSummarySheet newTSSheet = null;
        System.out.println("\n---------------------------------\n\nTEST open task summary sheet from first event:");
        try {
            newTSSheet = CatERing.getInstance().getKTManager().openTaskSummarySheet(event.getServices().get(0));
            System.out.println("Task summary sheet loaded:" + event.getServices().get(0).getTSSheet());
        } catch (UseCaseLogicException e) {
            System.out.println("task summary sheet loaded error" + e.getMessage());
            e.printStackTrace();
        }

        try {
            if (newTSSheet != tSSheet && tSSheet != null) {
                throw new Exception("Different tSSheet");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
