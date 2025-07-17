package clientbookstore.service;

import clientbookstore.comparator.book.AvailiableComporator;
import clientbookstore.comparator.book.LetterComporator;
import clientbookstore.comparator.book.PriceComporator;
import clientbookstore.comparator.book.YearComporator;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.enums.StatusBook;
import clientbookstore.model.Book;
import clientbookstore.collection.WareHouse;
import clientbookstore.repo.dao.BookDAO;

import java.sql.SQLException;
import java.util.List;

public class WareHouseService implements IService<Book> {

    @Inject
    private BookDAO bookDAO;
    //private WareHouse wareHouse;

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
        try {
            Book book = bookDAO.findById(item.getBookId());
            if (book != null) {
                System.out.println("Книга с таким id же существует, ее данные будут обновлены");
                bookDAO.update(item);
            } else {
                bookDAO.create(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail to add book " + item.getBookId() + " in warehouseSevice in add()" + e.getMessage());
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
