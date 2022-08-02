package businesslogic.kitchen.tasks;

import businesslogic.event.ServiceInfo;

import java.util.List;

public interface KitchenTasksEventReceiver {


    public void updateSSheetCreated(TaskSummarySheet tSSheet, ServiceInfo service);

    public void updateCookingItemAdded(List<KitchenTask> newKTask, TaskSummarySheet tSSheet);

    public void updateCookingItemDeleted(KitchenTask item, TaskSummarySheet tSSheet);

    public void updateKitchenTaskSpecification(KitchenTask kTask, TaskSummarySheet tSSheet);

    public void updateKitchenTaskAdded(KitchenTask kTask, TaskSummarySheet tSSheet);

    public void updateKitchenTaskMoved(TaskSummarySheet tSSheet, KitchenTask kitchenTask, int position);

    public void updateKitchenTaskAssignment(KitchenTask task, TaskSummarySheet tSSheet);

    public void updateEditAssignment(KitchenTask task, TaskSummarySheet tSSheet);

    public void updateKitchenTaskCompleted(KitchenTask task, TaskSummarySheet tSSheet);


}
