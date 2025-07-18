package clientbookstore.ui.actions.book;

import clientbookstore.controller.DataManager;
import clientbookstore.model.enums.TypeSortBooks;
import clientbookstore.model.entity.Book;
import clientbookstore.ui.actions.IAction;
import clientbookstore.ui.actions.order.CreateOrderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SortBooksByPriceAction implements IAction {

    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortBooksByPriceAction.class);

    public SortBooksByPriceAction(DataManager dataManager) {
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
