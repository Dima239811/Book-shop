package clientbookstore.ui.actions.order;

import clientbookstore.controller.MainContr;
import clientbookstore.model.exception.IncorrectNumberException;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CancelOrderAction implements IAction {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrderAction.class);
    private final MainContr dataManager;

    @Autowired
    public CancelOrderAction(MainContr dataManager) {
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
