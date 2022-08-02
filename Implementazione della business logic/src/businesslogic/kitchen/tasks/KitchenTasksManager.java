package businesslogic.kitchen.tasks;


import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.event.Turn;
import businesslogic.event.TurnsTable;
import businesslogic.recipe.CookingItem;
import businesslogic.recipe.Dosage;
import businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class KitchenTasksManager {
    private TaskSummarySheet tSSheet;
    private ArrayList<KitchenTasksEventReceiver> eventReceivers;


    public KitchenTasksManager() {
        TaskSummarySheet.loadAllTSSheets();
        this.eventReceivers = new ArrayList<>();
    }


    public TaskSummarySheet createTaskSummarySheet(ServiceInfo service) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (service.isMenuApproved() == false || !service.getEvent().isOwner(user) ||
                service.getMenu() == null) {
            throw new UseCaseLogicException();
        }
        if (TaskSummarySheet.getTSSheet(service.getId()) != null) {
            this.tSSheet = TaskSummarySheet.getTSSheet(service.getId());
            service.setTSSheet(tSSheet);
            return this.tSSheet;
        }
        this.tSSheet = new TaskSummarySheet(service);
        notifySSheetCreated(this.tSSheet, service);
        return this.tSSheet;
    }

    public TaskSummarySheet openTaskSummarySheet(ServiceInfo service) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (service.getTSSheet() == null || !service.getEvent().isOwner(user)) {
            throw new UseCaseLogicException();
        }
        this.tSSheet = service.getTSSheet();
        return this.tSSheet;
    }

    public List<KitchenTask> addCookingItem(CookingItem item) throws UseCaseLogicException {
        if (this.tSSheet == null) {
            throw new UseCaseLogicException();
        }
        List<KitchenTask> newKTask = this.tSSheet.addCookingItem(item);
        notifyCookingItemAdded(newKTask, tSSheet);
        return newKTask;
    }

    public void removeKitchenTask(KitchenTask item) throws UseCaseLogicException {
        if (this.tSSheet == null || !this.tSSheet.hasKitchenTask(item)) {
            throw new UseCaseLogicException();
        }
        this.tSSheet.removeKitchenTask(item);
        notifyCookingItemDeleted(item, this.tSSheet);
    }

    public void divideKitchenTask(KitchenTask kTask, Dosage dosage1, Dosage dosage2) throws UseCaseLogicException {
        if (this.tSSheet == null || !this.tSSheet.hasKitchenTask(kTask)) {
            throw new UseCaseLogicException();
        }
        KitchenTask copyTask = new KitchenTask(kTask.getCItem());
        if (dosage1 != null) {
            copyTask.setDosage(dosage1);
        }
        if (dosage2 != null) {
            kTask.setDosage(dosage1);
            notifyKitchenTaskSpecification(kTask, tSSheet);
        }
        this.tSSheet.addTask(copyTask);
        notifyKitchenTaskAdded(copyTask, tSSheet);
    }

    public void moveKitchenTask(KitchenTask kTask, int position) throws UseCaseLogicException {
        if (this.tSSheet == null || !this.tSSheet.hasKitchenTask(kTask)) {
            throw new UseCaseLogicException();
        }
        this.tSSheet.moveKitchenTask(kTask, position);
        notifyKitchenTaskMoved(tSSheet, kTask, position);
    }

    public List<Turn> seeTurnsTable() throws UseCaseLogicException {
        TurnsTable turnsTable = CatERing.getInstance().getTurnsTable();
        if (this.tSSheet == null) {
            throw new UseCaseLogicException();
        }
        return turnsTable.getAllTurns();
    }

    public KitchenTask assignsTask(KitchenTask task, Turn turn, User cook) throws UseCaseLogicException {
        TurnsTable turnsTable = CatERing.getInstance().getTurnsTable();
        if (this.tSSheet == null || !turnsTable.getAllTurns().contains(turn) || !tSSheet.hasKitchenTask(task) || (cook != null && !cook.isCook())) {
            throw new UseCaseLogicException();
        }
        task = tSSheet.assignsTask(task, turn, cook);
        notifyKitchenTaskAssignment(task, tSSheet);
        return task;
    }

    public KitchenTask editAssignment(KitchenTask task, Turn turn, User cook) throws UseCaseLogicException {
        TurnsTable turnsTable = CatERing.getInstance().getTurnsTable();
        if (this.tSSheet == null || (turn != null && !turnsTable.getAllTurns().contains(turn)) ||
                !tSSheet.hasKitchenTask(task) || (cook != null && !cook.isCook())) {
            throw new UseCaseLogicException();
        }

        task = tSSheet.editAssignment(task, turn, cook);
        notifyEditAssignment(task, tSSheet);
        return task;
    }

    public KitchenTask taskCompleted(KitchenTask task) throws UseCaseLogicException {
        if (this.tSSheet == null || !tSSheet.hasKitchenTask(task)) {
            throw new UseCaseLogicException();
        }

        tSSheet.taskCompleted(task);
        notifyKitchenTaskCompleted(task, tSSheet);
        return task;
    }


    public KitchenTask setAssignmentSpecifications(KitchenTask task, Long estimatedTime, Dosage dosage) throws UseCaseLogicException {
        if (this.tSSheet == null || !tSSheet.hasKitchenTask(task)) {
            throw new UseCaseLogicException();
        }

        task = tSSheet.setAssignmentSpecifications(task, estimatedTime, dosage);
        notifyKitchenTaskSpecification(task, tSSheet);
        return task;
    }


    private void notifyKitchenTaskMoved(TaskSummarySheet tSSheet, KitchenTask kitchenTask, int position) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateKitchenTaskMoved(tSSheet, kitchenTask, position);
        }
    }

    private void notifyKitchenTaskAdded(KitchenTask kTask, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateKitchenTaskAdded(kTask, tSSheet);
        }
    }

    private void notifyKitchenTaskSpecification(KitchenTask kTask, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateKitchenTaskSpecification(kTask, tSSheet);
        }
    }

    private void notifyCookingItemDeleted(KitchenTask item, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateCookingItemDeleted(item, tSSheet);
        }
    }

    private void notifyCookingItemAdded(List<KitchenTask> newKTask, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateCookingItemAdded(newKTask, tSSheet);
        }
    }

    private void notifySSheetCreated(TaskSummarySheet tSSheet, ServiceInfo service) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateSSheetCreated(this.tSSheet, service);
        }
    }

    private void notifyKitchenTaskAssignment(KitchenTask task, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateKitchenTaskAssignment(task, tSSheet);
        }
    }

    private void notifyEditAssignment(KitchenTask task, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateEditAssignment(task, tSSheet);
        }
    }

    private void notifyKitchenTaskCompleted(KitchenTask task, TaskSummarySheet tSSheet) {
        for (KitchenTasksEventReceiver er : eventReceivers) {
            er.updateKitchenTaskCompleted(task, tSSheet);
        }
    }


    public void addEventReceiver(KitchenTasksEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(KitchenTasksEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }
}
