package ui.actions.request;

import controller.DataManager;
import model.entity.RequestBook;
import ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SortRequestsByTitleAction implements IAction {
    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortRequestsByTitleAction.class);
    public SortRequestsByTitleAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: сортировка всех запросов по названию книги");
        System.out.println("Сортировка по алфавиту: ");
        List<RequestBook> requestBooks = dataManager.sortRequest("по алфавиту");

        if (requestBooks.isEmpty()) {
            logger.info("список запросов пуст");
            System.out.println("заказы не найдены");
        } else {
            requestBooks.forEach(book -> System.out.println(book));
            logger.info("выведено {} запросов на книги", requestBooks.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
