package clientbookstore.service.entityService;

import clientbookstore.model.comparator.book.AvailiableComporator;
import clientbookstore.model.comparator.book.LetterComporator;
import clientbookstore.model.comparator.book.PriceComporator;
import clientbookstore.model.comparator.book.YearComporator;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.model.enums.StatusBook;
import clientbookstore.model.entity.Book;
import clientbookstore.repo.dao.BookDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;


public class BookService implements IService<Book> {

    @Inject
    private BookDAO bookDAO;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public void writeOffBook(int bookId) {
        try {
            Book book = bookDAO.findById(bookId);
            if (book == null) {
                logger.warn("Книги с id: {} на складе нет", bookId);
                throw new RuntimeException("Книги с id: " + bookId + " на складе нет");
            }
            book.setStatus(StatusBook.OUT_OF_STOCK);

            bookDAO.update(book);

            logger.info("Книга с id: {} успешно списана", bookId);
        } catch (Exception e) {
            logger.error("Ошибка при списании книги с id: {}", bookId, e);
            throw new RuntimeException("Ошибка при списании книги", e);
        }
    }

    public List<Book> sortBooks(String criteria) {
        try {
            List<Book> books = bookDAO.getAll();
            return switch (criteria.toLowerCase()) {
                case "по алфавиту" -> {
                    books.sort(new LetterComporator());
                    yield books;
                }
                case "по цене" -> {
                    books.sort(new PriceComporator());
                    yield books;
                }
                case "по году издания" -> {
                    books.sort(new YearComporator());
                    yield books;
                }
                case "по наличию на складе" -> {
                    books.sort(new AvailiableComporator());
                    yield books;
                }
                default -> {
                    System.out.println("Ошибка: неопознанный критерий сортировки.");
                    yield books;
                }
            };
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get all book in warehouseSevice in sortBooks" + e.getMessage());
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            return bookDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get all book in warehouseSevice in getAll()" + e.getMessage());
        }
    }

    @Override
    public Book getById(int id) {
        try {
            return bookDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get book by id " + id + " in warehouseSevice in getById()" + e.getMessage());
        }
    }

    @Override
    public void add(Book item) {
        try {
            if (bookDAO.existsByNameAndAuthor(item.getName(), item.getAuthor())) {
                logger.warn("Книга '{}' автора '{}' уже существует",
                        item.getName(), item.getAuthor());
                throw new RuntimeException("Книга уже существует");
            }

            bookDAO.create(item);
            logger.info("Книга '{}' успешно добавлена", item.getName());
        } catch (Exception e) {
            logger.error("Ошибка при добавлении книги", e);
            throw new RuntimeException("Ошибка при добавлении книги", e);
        }
    }

    @Override
    public void update(Book item) {
        try {
            Book book = bookDAO.findById(item.getBookId());
            if (book == null) {
                logger.warn("Книга с id: {} не найдена", item.getBookId());
                throw new RuntimeException("Книга не найдена");
            }

            bookDAO.update(item);
            logger.info("Книга с id: {} успешно обновлена", item.getBookId());
        } catch (SQLException e) {
            logger.error("Ошибка при обновлении книги с id: {}", item.getBookId(), e);
            throw new RuntimeException("Fail to update book " + item.getBookId() + " in warehouseSevice in update()" + e.getMessage());
        }
    }
}
