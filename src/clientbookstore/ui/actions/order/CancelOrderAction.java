package ui.actions.order;

import controller.DataManager;
import model.exception.IncorrectNumberException;
import ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class CancelOrderAction implements IAction {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrderAction.class);
    private DataManager dataManager;

    public CancelOrderAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: отмена заказа");
        System.out.println("Введите id заказа для отмены");

        Scanner scanner = new Scanner(System.in);
        try {
            if (!scanner.hasNextInt()) {
                throw new IncorrectNumberException();
            }

            int id = scanner.nextInt();
            scanner.nextLine();

            if (id <= 0) {
                logger.error("Пользователь ввел число <= 0");
                throw new IncorrectNumberException("ID заказа должен быть положительным числом");
            }

            dataManager.cancelOrder(id);
            System.out.println("Заказ отменен");
            logger.info("Заказа с id {} успешно отменен", id);
        } catch (IncorrectNumberException e) {
            logger.info("Некорректный ввод числа {}", e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
