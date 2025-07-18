package clientbookstore.ui.actions.request;

import clientbookstore.controller.DataManager;

import clientbookstore.model.entity.RequestBook;
import clientbookstore.ui.actions.IAction;
import clientbookstore.ui.actions.order.SortOrdersByDateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowAllBookRequestsAction implements IAction {
    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(ShowAllBookRequestsAction.class);

    public ShowAllBookRequestsAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: просмотр всех запросов на книги");
        System.out.println("Список всех запросов");
        System.out.println("вывод из списка запросов");
        List<RequestBook> requestBooks = dataManager.getAllRequestBook();
        System.out.println("\n=== СПИСОК ВСЕХ запросов ===");
        System.out.println("Всего запросов: " + requestBooks.size());
        System.out.println("-----------------------------------------------");

        if (requestBooks.isEmpty()) {
            logger.info("список запросов пуст");
            System.out.println("запросы не найдены");
        } else {
            requestBooks.forEach(order -> System.out.println(order));
            logger.info("выведено {} запросов на книги", requestBooks.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
