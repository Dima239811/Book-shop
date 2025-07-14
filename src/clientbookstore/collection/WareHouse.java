package clientbookstore.collection;

import clientbookstore.comparator.book.AvailiableComporator;
import clientbookstore.comparator.book.LetterComporator;
import clientbookstore.comparator.book.PriceComporator;
import clientbookstore.comparator.book.YearComporator;
import clientbookstore.enums.StatusBook;
import clientbookstore.model.Book;

import java.util.ArrayList;
import java.util.List;

public class WareHouse implements ICollection<Book> {
    private List<Book> books = new ArrayList<>();

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
