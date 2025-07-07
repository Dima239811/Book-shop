package service;

import enums.StatusBook;
import model.Book;
import model.WareHouse;

import java.util.List;

public class WareHouseService {
    private final WareHouse wareHouse;

    public WareHouseService() {
        this.wareHouse = new WareHouse();
    }

    public boolean addBook(Book book) {
        return wareHouse.addBook(book);
    }

    public void writeOffBook(int bookId) {
        wareHouse.writeOffBookFromWareHouse(bookId);
    }

    public List<Book> getAllBooks() {
        return wareHouse.getBooks();
    }

    public List<Book> sortBooks(String criteria) {
        switch (criteria.toLowerCase()) {
            case "по алфавиту":
                return wareHouse.sortByName();
            case "по цене":
                return wareHouse.sortByPrice();
            case "по году издания":
                return wareHouse.sortByYear();
            case "по наличию на складе":
                return wareHouse.sortByStatus();
            default:
                System.out.println("Ошибка: неопознанный критерий сортировки.");
                return wareHouse.getBooks();
        }
    }

    public WareHouse getWareHouse() {
        return wareHouse;
    }

    public Book findBook(int id) {
        return wareHouse.findBook(id);
    }
}
