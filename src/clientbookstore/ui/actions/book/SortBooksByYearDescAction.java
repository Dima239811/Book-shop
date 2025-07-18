package clientbookstore.ui.actions.book;

import clientbookstore.controller.DataManager;
import clientbookstore.model.enums.TypeSortBooks;
import clientbookstore.model.entity.Book;
import clientbookstore.ui.actions.IAction;
import clientbookstore.ui.actions.order.CreateOrderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SortBooksByYearDescAction implements IAction {
    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortBooksByYearDescAction.class);

    public SortBooksByYearDescAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    @Override
    public void execute() {
        logger.info("пользователь выбрал сортировку книг по году издания");
        System.out.println("Сортировка книг по году издания");
        List<Book> bookList = dataManager.sortBooks(TypeSortBooks.BY_YEAR.getValue());

        if (bookList.isEmpty()) {
            logger.info("список книг пуст");
            System.out.println("Книги не найдены");
        } else {
            bookList.forEach(book -> System.out.println(book));
            logger.info("выведено {} книг", bookList.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
