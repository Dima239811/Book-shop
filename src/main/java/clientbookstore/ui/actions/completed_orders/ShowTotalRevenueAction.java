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
public class ShowTotalRevenueAction implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(ShowTotalRevenueAction.class);

    @Autowired
    public ShowTotalRevenueAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void execute() {
        logger.info("Выполнение действия: Подсчёт заработанных средств за период.");
        Scanner scanner = new Scanner(System.in);
        try {
            // Запрос периода у пользователя
            System.out.println("Введите начальную дату (формат: дд.мм.гггг):");
            Date from = parseDate(scanner.nextLine());

            System.out.println("Введите конечную дату (формат: дд.мм.гггг):");
            Date to = parseDate(scanner.nextLine());

            // Получение и вывод отсортированных заказов
            var price = dataManager.calculateIncomeForPeriod(from, to);

            System.out.println("за период заработано " + price);
            logger.info("Количество заработанных средств за период: {}", price);
        } catch (ParseException e) {
            logger.error("Ошибка парсинга даты {}", e.getMessage());
            System.out.println("Неверный формат даты. Используйте дд.мм.гггг");
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
    }
}
