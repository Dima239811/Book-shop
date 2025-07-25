package clientbookstore.ui.actions.csv.order;

import clientbookstore.controller.MainContr;
import clientbookstore.model.exception.DataImportException;

import clientbookstore.model.entity.Order;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ImportOrderAction implements IAction {
    private final MainContr dataManager;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(ImportOrderAction.class);

    @Autowired
    public ImportOrderAction(MainContr dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        logger.info("Запуск действия: импорт заказов в CSV.");
        try {
            System.out.println("\n=== Импорт заказов из CSV ===");
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine().trim();

            List<Order> importedOrders = dataManager.importOrdersFromCsv(path);
            System.out.printf("Успешно импортировано %d заказов\n", importedOrders.size());
            logger.info("Импорт заказов завершён успешно. Файл: {}", path);
        } catch (DataImportException e) {
            logger.error("Ошибка при импорте заказов: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при импорте заказов {}", e.getMessage());
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
