package clientbookstore.ui.actions.csv.book;

import clientbookstore.controller.MainContr;
import clientbookstore.model.exception.DataExportException;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExportBookAction implements IAction {
    private final MainContr dataManager;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(ExportBookAction.class);

    @Autowired
    public ExportBookAction(MainContr dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        logger.info("Запуск действия: экспорт книг в CSV.");
        try {
            System.out.println("\n=== Экспорт книг в CSV ===");
            System.out.print("Введите путь для сохранения файла: ");
            String path = scanner.nextLine().trim();

            dataManager.exportBooksToCsv(path);
            System.out.println("Экспорт успешно завершен");
            logger.info("Экспорт книг завершён успешно. Файл: {}", path);
        } catch (DataExportException e) {
            logger.error("Ошибка при экспорте книг: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при экспорте книг {}", e.getMessage());
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
