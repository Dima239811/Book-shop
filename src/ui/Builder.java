package ui;

import controller.DataManager;
import ui.action_factory.ActionFactory;
import ui.action_factory.DefaultActionFactory;
import ui.actions.book.AddBookAction;
import ui.menu_items.MenuItem;

public class Builder {
    private Menu rootMenu;
    private final DataManager dataManager;
    private final ActionFactory actionFactory;

    public Builder(ActionFactory actionFactory) {
        this.dataManager = new DataManager();
        this.actionFactory = actionFactory;
        buildMenu();
    }

    public void buildMenu() {
        rootMenu = new Menu("Главное меню");

        Menu booksMenu = new Menu("Операции с книгами");

        // Пункты операций
        booksMenu.addMenuItem(new MenuItem("Добавить книгу", actionFactory.createAddBookAction(),
                null));
        booksMenu.addMenuItem(new MenuItem("Списать книгу", actionFactory.writeOffBookAction(),
                null));

        rootMenu.addMenuItem(new MenuItem("Операции с книгами", null, booksMenu));
    }

    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
