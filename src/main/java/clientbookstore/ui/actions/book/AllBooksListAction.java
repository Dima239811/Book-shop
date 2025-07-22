package clientbookstore.ui.actions.book;

import clientbookstore.controller.MainContr;
import clientbookstore.model.entity.Book;
import clientbookstore.ui.actions.IAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class AllBooksListAction implements IAction {

    private static final Logger logger = LoggerFactory.getLogger(AllBooksListAction.class);

    @Autowired
    private final MainContr dataManager;

    public AllBooksListAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: вывод всех книг");

        System.out.println("Список всех книг");
        System.out.println("вывод из списка книг");
        List<Book> books = dataManager.getAllBooks();
        System.out.println("\n=== СПИСОК ВСЕХ КНИГ ===");
        System.out.println("Всего книг: " + books.size());
        System.out.println("-----------------------------------------------");

        if (books.isEmpty()) {
            logger.info("Список книг пуст");
            System.out.println("Книги не найдены");
        } else {
            books.forEach(book -> System.out.println(book));
            logger.info("Выведено {} книг", books.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
