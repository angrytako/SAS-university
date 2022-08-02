package test.kitchen.tasks;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.Turn;
import businesslogic.kitchen.tasks.KitchenTask;
import businesslogic.kitchen.tasks.TaskSummarySheet;

import java.util.List;

public class TestCatERing5a {
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


        List<KitchenTask> kItems = null;
        try {
            System.out.println("\n---------------------------------\n\nAdding new kitchenTask " + event.getServices().get(2).getMenu().getRecipes().get(0) + ":");
            kItems = CatERing.getInstance().getKTManager().addCookingItem(event.getServices().get(2).getMenu().getRecipes().get(0));
        } catch (UseCaseLogicException e) {
            System.out.println("Error TEST Adding new kitchenTask" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Tasks created: \n" + kItems);

        List<Turn> turns = null;
        try {
            System.out.println("\n---------------------------------\n\nSee turns table:");
            turns = CatERing.getInstance().getKTManager().seeTurnsTable();
        } catch (UseCaseLogicException e) {
            System.out.println("Error TEST Move task" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(turns);

        try {
            System.out.println("\n---------------------------------\n\nAssign task :" + kItems.get(0) +
                    "to cook: " + turns.get(0).getCooks().get(1) + "for the turn: " + turns.get(0));
            CatERing.getInstance().getKTManager().assignsTask(kItems.get(0), turns.get(0), turns.get(0).getCooks().get(1));
        } catch (UseCaseLogicException e) {
            System.out.println("Error TEST Assign task" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Kitchen task modified:");
        System.out.println(kItems.get(0).toCompleteString());
        System.out.println("Turns table modified:");
        System.out.println(turns);

        try {
            System.out.println("\n---------------------------------\n\n Edit task :" + kItems.get(0) +
                    "to cook: null  for the turn:null ");
            CatERing.getInstance().getKTManager().editAssignment(kItems.get(0), null, null);
        } catch (UseCaseLogicException e) {
            System.out.println("Error TEST Assign task" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Kitchen task modified:");
        System.out.println(kItems.get(0).toCompleteString());
        System.out.println("Turns table modified:");
        System.out.println(turns);


        if (kItems.get(0).getkCook() != null || kItems.get(0).getkTurn() != null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Error, task non modified" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
