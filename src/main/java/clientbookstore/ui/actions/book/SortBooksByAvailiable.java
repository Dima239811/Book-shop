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
public class SortBooksByAvailiable implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortBooksByAvailiable.class);

    @Autowired
    public SortBooksByAvailiable(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("начало сортировки книг по наличию на складе");
        System.out.println("Сортировка книг по наличию на складе");
        List<Book> bookList = dataManager.sortBooks(TypeSortBooks.BY_STOCKS_IN_WAREHOUSE.getValue());

        if (bookList.isEmpty()) {
            logger.info("список кнтг пуст");
            System.out.println("Книги не найдены");
        } else {
            bookList.forEach(book -> System.out.println(book));
            logger.info("выведено {} книг", bookList.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
