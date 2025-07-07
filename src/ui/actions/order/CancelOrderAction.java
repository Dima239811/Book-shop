package ui.actions.order;

import controller.DataManager;
import ui.actions.IAction;

import java.util.Scanner;

public class CancelOrderAction implements IAction {
    private DataManager dataManager;

    public CancelOrderAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        System.out.println("Введите id заказа для отмены");

        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();

        dataManager.cancelOrder(id);
        System.out.println("заказ отменен");
    }
}
