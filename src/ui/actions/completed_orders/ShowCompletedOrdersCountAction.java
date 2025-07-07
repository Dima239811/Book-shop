package ui.actions.completed_orders;

import controller.DataManager;
import ui.actions.IAction;

public class ShowCompletedOrdersCountAction implements IAction {
    private DataManager dataManager;

    public ShowCompletedOrdersCountAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void execute() {

    }
}
