package ui.action_factory;

import controller.DataManager;
import ui.actions.IAction;
import ui.actions.book.AddBookAction;
import ui.actions.book.WriteOffBookAction;

public class DefaultActionFactory implements ActionFactory{
    private final DataManager dataManager;

    public DefaultActionFactory(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public IAction createAddBookAction() {
        return new AddBookAction(dataManager);
    }

    @Override
    public IAction writeOffBookAction() {
        return new WriteOffBookAction(dataManager);
    }

}
