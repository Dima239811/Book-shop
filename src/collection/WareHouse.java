package collection;



import comparator.book.AvailiableComporator;
import comparator.book.LetterComporator;
import comparator.book.PriceComporator;
import comparator.book.YearComporator;
import enums.StatusBook;
import model.Book;
import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class WareHouse implements ICollection<Book> {
    private List<Book> books = new ArrayList<>();
//    private String NameWareHouse;
//    private String address;
//    private String email;
//    private final int maxCapacityBook;

//    public WareHouse() {
//        this.maxCapacityBook = 100;
//        this.email = "email";
//        this.address = "address";
//        NameWareHouse = "nameWareHouse";
//        this.books = new ArrayList<>();
//    }
//
//    public WareHouse(String nameWareHouse, String address, String email, int maxCapacityBook) {
//        this.books = new ArrayList<>();
//        NameWareHouse = nameWareHouse;
//        this.address = address;
//        this.email = email;
//        this.maxCapacityBook = maxCapacityBook;
//    }
//
////    public List<Book> getBooks() {
////        return books;
////    }

//    public String getNameWareHouse() {
//        return NameWareHouse;
//    }
//
//    public void setNameWareHouse(String nameWareHouse) {
//        NameWareHouse = nameWareHouse;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public int getMaxCapacityBook() {
//        return maxCapacityBook;
//    }

    // cписание книги со склада
    public void writeOffBookFromWareHouse(int bookId) {
        for (Book b: books) {
            if (b.getBookId() == bookId & b.getStatus().equals(StatusBook.IN_STOCK)) {
                b.setStatus(StatusBook.OUT_OF_STOCK);
                System.out.println("Статус книги изменен на - отсутсвует");
                return;
            }
        }
        System.out.println("Книга с id " + bookId + "  не найдена");
    }

//    public Book findBook(int id) {
//        for (Book b: books) {
//            if (b.getBookId() == id) {
//                return b;
//            }
//        }
//        return null;
//    }

    public List<Book> sortByName() {
        LetterComporator lettersComporators = new LetterComporator();
        books.sort(lettersComporators);
        return books;
    }

    public List<Book> sortByPrice() {
        PriceComporator priceComporators = new PriceComporator();
        books.sort(priceComporators);
        return books;
    }

    public List<Book> sortByYear() {
        books.sort(new YearComporator());
        return books;
    }

    public List<Book> sortByStatus() {
        books.sort(new AvailiableComporator());
        return books;
    }

//    public boolean updateBook(Book updatedBook) {
//        Book existing = findBook(updatedBook.getBookId());
//        if (existing != null) {
//            existing.setName(updatedBook.getName());
//            existing.setAuthtor(updatedBook.getAuthtor());
//            existing.setYear(updatedBook.getYear());
//            existing.setPrice(updatedBook.getPrice());
//            existing.setStatus(updatedBook.getStatus());
//            return true;
//        }
//        return false;
//    }

    @Override
    public void add(Book item) {
        books.add(item);
    }

    @Override
    public void update(Book item) {
        Book existing = findById(item.getBookId());
        if (existing != null) {
            existing.setName(item.getName());
            existing.setAuthtor(item.getAuthtor());
            existing.setYear(item.getYear());
            existing.setPrice(item.getPrice());
            existing.setStatus(item.getStatus());
        }
    }

    @Override
    public Book findById(int id) {
        return books.stream()
                .filter(c -> c.getBookId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return books;
    }
}
