package clientbookstore.ui.actions.csv.customer;

import clientbookstore.controller.MainContr;
import clientbookstore.model.exception.DataExportException;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ExportCustomerAction implements IAction {
    private final MainContr dataManager;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(ExportCustomerAction.class);

    public ExportCustomerAction(MainContr dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        logger.info("Запуск действия: экспорт клиентов в CSV.");
        try {
            System.out.println("\n=== Экспорт клиентов в CSV ===");
            System.out.print("Введите путь для сохранения файла: ");
            String path = scanner.nextLine().trim();

            dataManager.exportCustomersToCsv(path);
            System.out.println("Экспорт успешно завершен");
            logger.info("Экспорт клиентов завершён успешно. Файл: {}", path);
        } catch (DataExportException e) {
            logger.error("Ошибка при экспорте клиентов: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при экспорте клиентов {}", e.getMessage());
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
