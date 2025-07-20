package clientbookstore.ui.actions.completed_orders;

import clientbookstore.controller.MainContr;
import clientbookstore.model.entity.Order;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SortCompletedOrdersByPriceAction implements IAction {
    private MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortCompletedOrdersByPriceAction.class);

    public SortCompletedOrdersByPriceAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Выполнение действия: Сортировка выполненных заказов по цене за период.");
        Scanner scanner = new Scanner(System.in);
        try {
            // Запрос периода у пользователя
            System.out.println("Введите начальную дату (формат: дд.мм.гггг):");
            Date from = parseDate(scanner.nextLine());

            System.out.println("Введите конечную дату (формат: дд.мм.гггг):");
            Date to = parseDate(scanner.nextLine());

            // Получение и вывод отсортированных заказов
            List<Order> orders = dataManager.sortPerformOrdersForPeriod("по цене", from, to);

            if (orders.isEmpty()) {
                System.out.println("В указанный период выполненные заказы не найдены.");
                logger.info("В период с {} по {} заказы не найдены", from, to);
            } else {
                System.out.println("\nРезультаты (" + orders.size() + " заказов):");
                System.out.println("-----------------------------------------------");
                orders.forEach(order -> System.out.println(order));
                System.out.println("-----------------------------------------------");
                logger.info("В период с {} по {} отсортировано {} заказов по цене", from, to, orders.size());
            }
        } catch (ParseException e) {
            logger.error("Ошибка парсинга даты {}", e.getMessage());
            System.out.println("Неверный формат даты. Используйте дд.мм.гггг");
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
    }
}
