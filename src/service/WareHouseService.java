package service;

import model.Book;
import collection.WareHouse;
import model.Customer;

import java.util.List;

public class WareHouseService implements IService<Book> {
    private final WareHouse wareHouse;

    public WareHouseService() {
        this.wareHouse = new WareHouse();
    }

    public void writeOffBook(int bookId) {
        wareHouse.writeOffBookFromWareHouse(bookId);
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
                return wareHouse.getAll();
        }
    }

    public WareHouse getWareHouse() {
        return wareHouse;
    }

    @Override
    public List<Book> getAll() {
        return wareHouse.getAll();
    }

    @Override
    public Book getById(int id) {
        return wareHouse.findById(id);
    }

    @Override
    public void add(Book item) {
        Book existing = wareHouse.findById(item.getBookId());
        if (existing != null) {
            update(item);
        } else {
            wareHouse.add(item);
        }
    }

    @Override
    public void update(Book item) {
        wareHouse.update(item);
    }


}
