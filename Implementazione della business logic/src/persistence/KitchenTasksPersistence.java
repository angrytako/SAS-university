package persistence;

import businesslogic.event.ServiceInfo;
import businesslogic.kitchen.tasks.KitchenTask;
import businesslogic.kitchen.tasks.KitchenTasksEventReceiver;
import businesslogic.kitchen.tasks.TaskSummarySheet;

import java.util.List;

public class KitchenTasksPersistence implements KitchenTasksEventReceiver {
    @Override
    public void updateSSheetCreated(TaskSummarySheet tSSheet, ServiceInfo service) {
        TaskSummarySheet.saveNewTaskSummarySheet(tSSheet, service);
    }

    @Override
    public void updateCookingItemAdded(List<KitchenTask> newKTask, TaskSummarySheet tSSheet) {
        KitchenTask.saveAllNewKitchenTasks(newKTask, tSSheet.getId(), tSSheet.getPosition(newKTask.get(0)));
    }

    @Override
    public void updateCookingItemDeleted(KitchenTask item, TaskSummarySheet tSSheet) {
        KitchenTask.deleteKitchenTask(item);
    }

    @Override
    public void updateKitchenTaskSpecification(KitchenTask kTask, TaskSummarySheet tSSheet) {
        KitchenTask.saveSpecification(kTask);
    }

    @Override
    public void updateKitchenTaskAdded(KitchenTask kTask, TaskSummarySheet tSSheet) {
        KitchenTask.saveNewKitchenTask(kTask, tSSheet.getId(), tSSheet.getPosition(kTask));
    }

    @Override
    public void updateKitchenTaskMoved(TaskSummarySheet tSSheet, KitchenTask kitchenTask, int position) {
        for (KitchenTask kTask : tSSheet.getAllTasksFromPosition(position)) {
            KitchenTask.savePosition(kTask, position);
            position++;
        }
    }

    @Override
    public void updateKitchenTaskAssignment(KitchenTask task, TaskSummarySheet tSSheet) {
        KitchenTask.saveAssignment(task);
    }

    @Override
    public void updateEditAssignment(KitchenTask task, TaskSummarySheet tSSheet) {
        KitchenTask.saveAssignment(task);
    }

    @Override
    public void updateKitchenTaskCompleted(KitchenTask task, TaskSummarySheet tSSheet) {
        KitchenTask.saveCompleted(task);
    }
}
