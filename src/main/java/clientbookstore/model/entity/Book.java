package clientbookstore.model.entity;


import clientbookstore.model.converters.StatusBookConverter;
import clientbookstore.model.enums.StatusBook;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private double price;

    @Convert(converter = StatusBookConverter.class)
    @Column(nullable = false, name = "status")
    private StatusBook status;  // в наличии или отсутствует

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int bookId;

    public Book() { }

    public Book(String name, String author, int year, double price, StatusBook status) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    public Book(String name, String author, int year, double price, StatusBook status, int bookId) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
        this.status = status;
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(StatusBook statusBook) {
        this.status = statusBook;
    }

    public StatusBook getStatus() {
        return status;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return String.format("Книга: %s | Автор: %s | Год: %d | Цена: %.2f | %s | ID: %d",
                name,
                author,
                year,
                price,
                status.getValue(),
                bookId);
    }
}
