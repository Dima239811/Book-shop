package ui.action_factory;

import ui.actions.IAction;

public interface ActionFactory {
    IAction createAddBookAction();
    IAction writeOffBookAction();
}
