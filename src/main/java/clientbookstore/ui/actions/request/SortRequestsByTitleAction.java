package clientbookstore.ui.actions.request;

import clientbookstore.controller.MainContr;
import clientbookstore.model.entity.RequestBook;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortRequestsByTitleAction implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortRequestsByTitleAction.class);

    @Autowired
    public SortRequestsByTitleAction(MainContr dataManager) {
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
