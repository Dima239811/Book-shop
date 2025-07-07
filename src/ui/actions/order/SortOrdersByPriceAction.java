package ui.actions.order;

import controller.DataManager;
import model.Order;
import ui.actions.IAction;

import java.util.List;

public class SortOrdersByPriceAction implements IAction {
    private DataManager dataManager;

    public SortOrdersByPriceAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        System.out.println("Сортировка по статусу выполнения: ");
        List<Order> orders = dataManager.sortOrders("по цене");

        if (orders.isEmpty()) {
            System.out.println("заказы не найдены");
        } else {
            orders.forEach(book -> System.out.println(book));
        }

        System.out.println("-----------------------------------------------");
    }
}
