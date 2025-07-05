package controller;

import enums.OrderStatus;
import model.Book;
import model.Customer;
import model.Order;
import model.RequestBook;
import service.*;

import java.util.Date;
import java.util.List;

public class DataManager {
    private final WareHouseService wareHouseService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final RequestBookService requestService;

    public DataManager() {
        this.wareHouseService = new WareHouseService();
        this.orderService = new OrderService();
        this.customerService = new CustomerService();
        this.requestService = new RequestBookService();
    }

    public void writeOffBook(int bookId) {
        wareHouseService.writeOffBook(bookId);
    }

    public void createOrder(Book book, Customer customer, Date orderDate) {
        orderService.createOrder(book, customer, orderDate);
    }

    public void cancelOrder(int orderId) {
        orderService.cancelOrder(orderId);
    }

    public void changeStatusOrder(int orderId, OrderStatus status) {
        orderService.changeOrderStatus(orderId, status);
    }

    public List<Order> sortOrders(String criteria) {
        return orderService.sortOrders(criteria);
    }

    public void addBookToWareHouse(Book book) {
        if (wareHouseService.addBook(book)) {
            requestService.closeRequest(book);
        }
    }

    public void addRequest(Book book, Customer customer) {
        requestService.addRequest(customer, book);
    }

    public List<Book> sortBooks(String criteria) {
        return wareHouseService.sortBooks(criteria);
    }

    public List<Book> getAllBooks() {
        return wareHouseService.getAllBooks();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void setCustomers(List<Customer> customers) {
        customerService.setCustomers(customers);
    }

    public List<RequestBook> sortRequest(String criteria) {
        return requestService.sortRequest(criteria);
    }

    public List<RequestBook> getAllRequestBook() {
        return requestService.getAllRequestBook();
    }

    public List<Order> sortPerformOrdersForPeriod(String criteria, Date from, Date to) {
        return orderService.sortPerformOrders(criteria, from, to);
    }

    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    public double calculateIncomeForPeriod(Date from, Date to) {
        return orderService.calculateIncomeForPerioud(from, to);
    }
}
