package ui.actions.book;

import controller.DataManager;
import model.entity.Book;
import ui.actions.IAction;
import util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowStaleBooksAction implements IAction {
    private final DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(ShowStaleBooksAction.class);

    public ShowStaleBooksAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: просмотр залежавшихся книг");
        try {
            int staleMonths = AppConfig.getStaleBookMonths();
            List<Book> staleBooks = dataManager.getStaleBooks(staleMonths);

            if (staleBooks.isEmpty()) {
                logger.info("Залежавшихся книг за последние {} месяцев не найдено", staleMonths);
                System.out.println("Нет залежавшихся книг.");
            } else {
                System.out.println("Список залежавшихся книг (не продавались более "
                        + staleMonths + " месяцев):");
                staleBooks.forEach(book ->
                        System.out.println("• " + book.getName() + " (ID: " + book.getBookId() + ")"));
                logger.info("Найдено {} залежавшихся книг", staleBooks.size());
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении списка залежавшихся книг", e);
            System.out.println("Ошибка при получении списка залежавшихся книг: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
