package clientbookstore.ui.actions.order;

import clientbookstore.controller.DataManager;
import clientbookstore.model.enums.OrderStatus;
import clientbookstore.model.exception.IncorrectNumberException;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ChangeOrderStatusAction implements IAction {
    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(ChangeOrderStatusAction.class);

    public ChangeOrderStatusAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: изменение статуса заказа");
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите id заказа для изменения статуса:");
            if (!scanner.hasNextInt()) {
                throw new IncorrectNumberException("Ошибка: ID должен быть числом.");
            }
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Выберите цифру статуса заказа:");
            System.out.println("1. " + OrderStatus.NEW.getValue());
            System.out.println("2. " + OrderStatus.COMPLETED.getValue());
            System.out.println("3. " + OrderStatus.CANCELLED.getValue());
            System.out.println("4. " + OrderStatus.PROCESSING.getValue());

            if (!scanner.hasNextInt()) {
                throw new IncorrectNumberException("Ошибка: Введите число от 1 до 4.");
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            OrderStatus selectedStatus;
            switch (choice) {
                case 1: selectedStatus = OrderStatus.NEW; break;
                case 2: selectedStatus = OrderStatus.COMPLETED; break;
                case 3: selectedStatus = OrderStatus.CANCELLED; break;
                case 4: selectedStatus = OrderStatus.PROCESSING; break;
                default: throw new IncorrectNumberException("Некорректный вариант");
            }

            dataManager.changeStatusOrder(id, selectedStatus);
            System.out.println("Статус заказа успешно изменен.");
            logger.info("Статус заказа с id {} успешно изменен", id);
        } catch (IncorrectNumberException e) {
            logger.info("Некорректный ввод числа {}", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            logger.info("Возникла ошибка " + e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
