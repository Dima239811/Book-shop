package clientbookstore.ui.actions.completed_orders;

import clientbookstore.controller.MainContr;

import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Component
public class ShowCompletedOrdersCountAction implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(ShowCompletedOrdersCountAction.class);

    @Autowired
    public ShowCompletedOrdersCountAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    public void execute() {
        logger.info("Выполнение действия: Подсчёт выполненных заказов за период.");
        Scanner scanner = new Scanner(System.in);
        try {
            // Запрос периода у пользователя
            System.out.println("Введите начальную дату (формат: дд.мм.гггг):");
            Date from = parseDate(scanner.nextLine());

            System.out.println("Введите конечную дату (формат: дд.мм.гггг):");
            Date to = parseDate(scanner.nextLine());

            int count = dataManager.getCountPerformedOrder(from, to);

            System.out.println("Количество выполненных заказов " + count + " за период с " + from + " по " + to);
            logger.info("Количество выполненных заказов за период: {}", count);
        } catch (ParseException e) {
            logger.error("Ошибка парсинга даты {}", e.getMessage());
            System.out.println("Неверный формат даты. Используйте дд.мм.гггг");
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
    }
}
