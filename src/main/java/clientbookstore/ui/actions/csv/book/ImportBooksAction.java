package clientbookstore.ui.actions.csv.book;

import clientbookstore.controller.MainContr;
import clientbookstore.model.exception.DataImportException;
import clientbookstore.model.entity.Book;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ImportBooksAction implements IAction {
    private final MainContr dataManager;
    private final Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(ImportBooksAction.class);

    @Autowired
    public ImportBooksAction(MainContr dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        logger.info("Запуск действия: импорт книг в CSV.");
        try {
            System.out.println("\n=== Импорт книг из CSV ===");
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine().trim();

            List<Book> importedBooks = dataManager.importBooksFromCsv(path);
            System.out.printf("Успешно импортировано %d книг\n", importedBooks.size());
            logger.info("Импорт книг завершён успешно. Файл: {}", path);
        } catch (DataImportException e) {
            logger.error("Ошибка при импорте книг: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при импорте книг {}", e.getMessage());
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
