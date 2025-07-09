package controller;

import csv.*;
import enums.OrderStatus;
import model.Book;
import model.Customer;
import model.Order;
import model.RequestBook;
import service.*;

import java.util.Date;
import java.util.List;

public class DataManager {
    private static final DataManager INSTANCE = new DataManager();
    private final WareHouseService wareHouseService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final RequestBookService requestService;
    private final ICsvService<Book> bookCsvService;
    private final ICsvService<Order> orderCsvService;
    private final ICsvService<Customer> customerCsvService;
    private final ICsvService<RequestBook> requestBookCsvService;

    private DataManager() {
        this.wareHouseService = new WareHouseService();
        this.orderService = new OrderService();
        this.customerService = new CustomerService();
        this.requestService = new RequestBookService();
        this.bookCsvService = new BookCsvService();
        this.orderCsvService = new OrderCsvService();
        this.customerCsvService = new CustomerCsvService();
        this.requestBookCsvService = new RequestBookCsvService();
    }
    public static DataManager getInstance() {
        return INSTANCE;
    }

    public void writeOffBook(int bookId) {
        wareHouseService.writeOffBook(bookId);
    }

    public Book findBook(int id) {
        return wareHouseService.findBook(id);
    }

    public void exportBooksToCsv(String filePath) throws Exception {
        List<Book> books = getAllBooks();
        System.out.println("передано на экспорт " + books.size() + " книг");
        bookCsvService.exportToCsv(books, filePath);
    }

    public List<Book> importBooksFromCsv(String filePath) throws Exception {
        List<Book> imported = bookCsvService.importFromCsv(filePath);
        for (Book b: imported) {
            wareHouseService.addBook(b);
        }
        return imported;
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

    public void exportOrdersToCsv(String filePath) throws Exception {
        List<Order> orders = getAllOrder();
        orderCsvService.exportToCsv(orders, filePath);
    }

    public List<Order> importOrdersFromCsv(String filePath) throws Exception {
        List<Order> imported = orderCsvService.importFromCsv(filePath);
        for (Order b: imported) {
            orderService.addOrder(b);
            wareHouseService.addBook(b.getBook());
            customerService.addCustomer(b.getCustomer());
        }
        return imported;
    }

    public void addBookToWareHouse(Book book) {
        if (wareHouseService.addBook(book)) {
            requestService.closeRequest(book);
        }
    }

    public void addRequest(Book book, Customer customer) {
        requestService.addRequest(customer, book);
    }

    public void exportRequestToCsv(String filePath) throws Exception {
        List<RequestBook> requestBooks = getAllRequestBook();
        requestBookCsvService.exportToCsv(requestBooks, filePath);
    }

    public List<RequestBook> importRequestFromCsv(String filePath) throws Exception {
        List<RequestBook> imported = requestBookCsvService.importFromCsv(filePath);
        for (RequestBook b: imported) {
            requestService.addRequest(b);
            wareHouseService.addBook(b.getBook());
            customerService.addCustomer(b.getCustomer());
        }
        return imported;
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

    public void exportCustomersToCsv(String filePath) throws Exception {
        List<Customer> customers = getAllCustomers();
        System.out.println("передано на экспорт " + customers.size() + " клиентов");
        customerCsvService.exportToCsv(customers, filePath);
    }

    public List<Customer> importCustomersFromCsv(String filePath) throws Exception {
        List<Customer> imported = customerCsvService.importFromCsv(filePath);
        for (Customer b: imported) {
            customerService.addCustomer(b);
        }
        return imported;
    }

//    public void setCustomers(List<Customer> customers) {
//        customerService.setCustomers(customers);
//    }

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

    public int getCountPerformedOrder(Date from, Date to) {
        return orderService.getCountPerformedOrder(from ,to);
    }
}
