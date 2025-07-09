package model;


import enums.RequestStatus;
import enums.StatusBook;

public class RequestBook {
    private Customer customer;
    private Book book;
    private RequestStatus status;
    private int id;
    private static int countId = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestBook(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
        this.status = RequestStatus.OPEN;
        this.id = countId;
        countId++;
    }

    public RequestBook(Customer customer, Book book, RequestStatus status, int id) {
        this.customer = customer;
        this.book = book;
        this.status = status;
        this.id = id;
        countId++;
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
}
