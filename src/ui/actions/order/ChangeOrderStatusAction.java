package ui.actions.order;

import controller.DataManager;
import enums.OrderStatus;
import ui.actions.IAction;

import java.util.Scanner;

public class ChangeOrderStatusAction implements IAction {
    private DataManager dataManager;

    public ChangeOrderStatusAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        System.out.println("введите id заказа для изменения статуса");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        System.out.println("Выберите цифру статуса заказа, который вы хотите поставить");
        System.out.println("1. " + OrderStatus.NEW.getValue());
        System.out.println("2. " + OrderStatus.COMPLETED.getValue());
        System.out.println("3. " + OrderStatus.CANCELLED.getValue());
        System.out.println("4. " + OrderStatus.PROCESSING.getValue());

        int choise = scanner.nextInt();

        OrderStatus selectedStatus = null;
        switch (choise) {
            case 1:
                selectedStatus = OrderStatus.NEW;
                break;
            case 2:
                selectedStatus = OrderStatus.COMPLETED;
                break;
            case 3:
                selectedStatus = OrderStatus.CANCELLED;
                break;
            case 4:
                selectedStatus = OrderStatus.PROCESSING;
                break;
            default:
                System.out.println("некорректный вариант");
                return;
        }

        dataManager.changeStatusOrder(id, selectedStatus);
    }
}
