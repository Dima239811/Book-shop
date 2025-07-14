package clientbookstore.ui.actions.order;

import clientbookstore.controller.DataManager;

import clientbookstore.model.Order;
import clientbookstore.ui.actions.IAction;

import java.util.List;

public class ShowAllOrdersAction implements IAction {
    private DataManager dataManager;

    public ShowAllOrdersAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        System.out.println("Список всех заказов");
        System.out.println("вывод из списка заказов");
        List<Order> oders = dataManager.getAllOrder();
        System.out.println("\n=== СПИСОК ВСЕХ заказов ===");
        System.out.println("Всего заказов: " + oders.size());
        System.out.println("-----------------------------------------------");

        if (oders.isEmpty()) {
            System.out.println("заказы не найдены");
        } else {
            oders.forEach(order -> System.out.println(order));
        }

        System.out.println("-----------------------------------------------");
    }
}
