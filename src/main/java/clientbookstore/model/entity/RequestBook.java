package clientbookstore.model.entity;


import clientbookstore.model.converters.RequestStatusConverter;
import clientbookstore.model.enums.RequestStatus;
import clientbookstore.model.enums.StatusBook;
import jakarta.persistence.*;

@Entity
@Table(name = "request")
public class RequestBook {
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @Convert(converter = RequestStatusConverter.class)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public RequestBook() { }

    public RequestBook(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
        this.status = RequestStatus.OPEN;
    }

    public RequestBook(Customer customer, Book book, RequestStatus status, int id) {
        this.customer = customer;
        this.book = book;
        this.status = status;
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setStatusBook(StatusBook statusBook) {
        System.out.println("статус книги изменен на " + statusBook);
        book.setStatus(statusBook);
    }

    @Override
    public String toString() {
        return "Запрос на книгу:" +
                "\nКлиент: " + customer.getFullName() + " (ID: " + customer.getCustomerID() + ")" +
                "\nКнига: " + book.getName() + " (ID: " + book.getBookId() + ")" +
                "\nСтатус запроса: " + status +
                "\nСтатус книги: " + book.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
