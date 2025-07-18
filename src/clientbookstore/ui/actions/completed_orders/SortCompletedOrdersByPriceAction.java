package clientbookstore.ui.actions.completed_orders;

import clientbookstore.controller.DataManager;
import clientbookstore.model.entity.Order;
import clientbookstore.ui.actions.IAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SortCompletedOrdersByPriceAction implements IAction {
    private DataManager dataManager;

    public SortCompletedOrdersByPriceAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
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
            } else {
                System.out.println("\nРезультаты (" + orders.size() + " заказов):");
                System.out.println("-----------------------------------------------");
                orders.forEach(order -> System.out.println(order));
                System.out.println("-----------------------------------------------");
            }
        } catch (ParseException e) {
            System.out.println("Неверный формат даты. Используйте дд.мм.гггг");
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
    }
}
