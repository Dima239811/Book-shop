package clientbookstore.service.entityService;

import clientbookstore.model.comparator.book.AvailiableComporator;
import clientbookstore.model.comparator.book.LetterComporator;
import clientbookstore.model.comparator.book.PriceComporator;
import clientbookstore.model.comparator.book.YearComporator;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.model.enums.StatusBook;
import clientbookstore.model.entity.Book;
import clientbookstore.repo.dao.BookDAO;
import clientbookstore.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
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
                System.out.println("Книги с id: " + bookId + " на складе нет");
                return;
            }
            book.setStatus(StatusBook.OUT_OF_STOCK);
            try {
                bookDAO.update(book);
            } catch (SQLException e) {
                throw new RuntimeException("Fail in warehouseService updatebook book " + e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail in warehouseService find book " + e);
        }
    }

    public List<Book> sortBooks(String criteria) {
        try {
            List<Book> books = bookDAO.getAll();
            switch (criteria.toLowerCase()) {
                case "по алфавиту":
                    books.sort(new LetterComporator());
                    return books;
                case "по цене":
                    books.sort(new PriceComporator());
                    return books;
                case "по году издания":
                    books.sort(new YearComporator());
                    return books;
                case "по наличию на складе":
                    books.sort(new AvailiableComporator());
                    return books;
                default:
                    System.out.println("Ошибка: неопознанный критерий сортировки.");
                    return books;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get all book in warehouseSevice in sortBooks" + e.getMessage());
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            List<Book> books = bookDAO.getAll();
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get all book in warehouseSevice in getAll()" + e.getMessage());
        }
    }

    @Override
    public Book getById(int id) {
        try {
            Book book = bookDAO.findById(id);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get book by id " + id + " in warehouseSevice in getById()" + e.getMessage());
        }
    }

    @Override
    public void add(Book item) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;

        try {
            if (bookDAO.existsByNameAndAuthor(item.getName(), item.getAuthor())) {
                logger.warn("Книга '{}' автора '{}' уже существует",
                        item.getName(), item.getAuthor());
                throw new RuntimeException("Книга уже существует");
            }

            tx = session.beginTransaction();
            bookDAO.create(item);
            tx.commit();
            logger.info("Книга '{}' успешно добавлена", item.getName());

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.error("Ошибка при добавлении книги", e);
            throw new RuntimeException("Ошибка при добавлении книги", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Book item) {
        try {
            bookDAO.update(item);
        } catch (SQLException e) {
            throw new RuntimeException("Fail to update book " + item.getBookId() + " in warehouseSevice in update()" + e.getMessage());
        }
    }
}
