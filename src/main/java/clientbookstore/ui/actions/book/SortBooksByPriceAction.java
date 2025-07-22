package clientbookstore.ui.actions.book;

import clientbookstore.controller.MainContr;
import clientbookstore.model.enums.TypeSortBooks;
import clientbookstore.model.entity.Book;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortBooksByPriceAction implements IAction {
    private final MainContr dataManager;

    private static final Logger logger = LoggerFactory.getLogger(SortBooksByPriceAction.class);

    @Autowired
    public SortBooksByPriceAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("пользователь выбрал сортировку книг по цене");
        System.out.println("Сортировка по цене: ");
        List<Book> books = dataManager.sortBooks(TypeSortBooks.BY_PRICE.getValue());

        if (books.isEmpty()) {
            logger.info("список книг пуст");
            System.out.println("Книги не найдены");
        } else {
            books.forEach(book -> System.out.println(book));
            logger.info("выведено {} книг", books.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
