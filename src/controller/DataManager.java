package controller;

import csv.BookCsvService;
import enums.OrderStatus;
import exception.DataExportException;
import exception.DataImportException;
import model.Book;
import model.Customer;
import model.Order;
import model.RequestBook;
import org.w3c.dom.ls.LSOutput;
import service.*;

import java.util.Date;
import java.util.List;

public class DataManager {
    private static final DataManager INSTANCE = new DataManager();
    private final WareHouseService wareHouseService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final RequestBookService requestService;
    private final BookCsvService bookCsvService;

    private DataManager() {
        this.wareHouseService = new WareHouseService();
        this.orderService = new OrderService();
        this.customerService = new CustomerService();
        this.requestService = new RequestBookService();
        this.bookCsvService = new BookCsvService();
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

    public void exportBooksToCsv(String filePath) throws DataExportException {
        List<Book> books = getAllBooks();
        //System.out.println("[DEBUG] Передано в экспорт: " + books.size() + " книг");
        bookCsvService.exportToCsv(books, filePath);
    }

    public List<Book> importBooksFromCsv(String filePath, boolean update) throws DataImportException {
        List<Book> imported = bookCsvService.importFromCsv(filePath);
        imported.forEach(book -> {
            if (update && wareHouseService.findBook(book.getBookId()) != null) {
                wareHouseService.updateBook(book);
            } else {
                wareHouseService.addBook(book);
            }
        });
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
