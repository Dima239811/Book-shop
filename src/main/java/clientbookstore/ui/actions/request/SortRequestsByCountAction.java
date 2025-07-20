package clientbookstore.ui.actions.request;

import clientbookstore.controller.MainContr;

import clientbookstore.model.entity.RequestBook;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SortRequestsByCountAction implements IAction {

    private MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortRequestsByCountAction.class);
    public SortRequestsByCountAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: сортировка всех запросов по количеству");
        System.out.println("Сортировка по кол-ву запросов: ");
        List<RequestBook> requestBooks = dataManager.sortRequest("по количеству запросов");

        if (requestBooks.isEmpty()) {
            logger.info("список запросов пуст");
            System.out.println("запросы не найдены");
        } else {
            requestBooks.forEach(book -> System.out.println(book));
            logger.info("выведено {} запросов на книги", requestBooks.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
