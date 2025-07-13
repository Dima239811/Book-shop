package controller;

import csv.*;
import dependesies.annotation.Inject;
import enums.OrderStatus;
import enums.StatusBook;
import model.*;
import service.*;
import util.AppConfig;
import util.JsonUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    //private static final DataManager INSTANCE = new DataManager();
    @Inject
    private WareHouseService wareHouseService;

    @Inject
    private OrderService orderService;

    @Inject
    private CustomerService customerService;

    @Inject
    private RequestBookService requestService;

    @Inject
    private ICsvService<Book> bookCsvService;

    @Inject
    private ICsvService<Order> orderCsvService;

    @Inject
    private ICsvService<Customer> customerCsvService;

    @Inject
    private ICsvService<RequestBook> requestBookCsvService;

//    private DataManager() {
//        this.wareHouseService = new WareHouseService();
//        this.orderService = new OrderService();
//        this.customerService = new CustomerService();
//        this.requestService = new RequestBookService();
//        this.bookCsvService = new BookCsvService();
//        this.orderCsvService = new OrderCsvService();
//        this.customerCsvService = new CustomerCsvService();
//        this.requestBookCsvService = new RequestBookCsvService();
//    }

//    public static DataManager getInstance() {
//        return INSTANCE;
//    }

    public void writeOffBook(int bookId) {
        wareHouseService.writeOffBook(bookId);
    }

    public Book findBook(int id) {
        return wareHouseService.getById(id);
    }

    public void exportBooksToCsv(String filePath) throws Exception {
        List<Book> books = getAllBooks();
        System.out.println("передано на экспорт " + books.size() + " книг");
        bookCsvService.exportToCsv(books, filePath);
    }

    public List<Book> importBooksFromCsv(String filePath) throws Exception {
        List<Book> imported = bookCsvService.importFromCsv(filePath);
        for (Book b : imported) {
            wareHouseService.add(b);
        }
        return imported;
    }

    public void createOrder(Order order) {
        wareHouseService.add(order.getBook());
        customerService.add(order.getCustomer());
        orderService.add(order);
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
        for (Order b : imported) {
            orderService.add(b);
            wareHouseService.add(b.getBook());
            customerService.add(b.getCustomer());
        }
        return imported;
    }

    public void addBookToWareHouse(Book book) {
        wareHouseService.add(book);

        if (AppConfig.isAutoCloseRequestsEnabled()) {
            requestService.closeRequest(book);
        }
    }

    public void addRequest(RequestBook requestBook) {
        wareHouseService.add(requestBook.getBook());
        customerService.add(requestBook.getCustomer());
        requestService.add(requestBook);
    }

    public void exportRequestToCsv(String filePath) throws Exception {
        List<RequestBook> requestBooks = getAllRequestBook();
        requestBookCsvService.exportToCsv(requestBooks, filePath);
    }

    public List<RequestBook> importRequestFromCsv(String filePath) throws Exception {
        List<RequestBook> imported = requestBookCsvService.importFromCsv(filePath);
        for (RequestBook b : imported) {
            requestService.add(b);
            wareHouseService.add(b.getBook());
            customerService.add(b.getCustomer());
        }
        return imported;
    }

    public List<Book> sortBooks(String criteria) {
        return wareHouseService.sortBooks(criteria);
    }

    public List<Book> getAllBooks() {
        return wareHouseService.getAll();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    public void exportCustomersToCsv(String filePath) throws Exception {
        List<Customer> customers = getAllCustomers();
        System.out.println("передано на экспорт " + customers.size() + " клиентов");
        customerCsvService.exportToCsv(customers, filePath);
    }

    public List<Customer> importCustomersFromCsv(String filePath) throws Exception {
        List<Customer> imported = customerCsvService.importFromCsv(filePath);
        for (Customer b : imported) {
            customerService.add(b);
        }
        return imported;
    }

    public List<RequestBook> sortRequest(String criteria) {
        return requestService.sortRequest(criteria);
    }

    public List<RequestBook> getAllRequestBook() {
        return requestService.getAll();
    }

    public List<Order> sortPerformOrdersForPeriod(String criteria, Date from, Date to) {
        return orderService.sortPerformOrders(criteria, from, to);
    }

    public List<Order> getAllOrder() {
        return orderService.getAll();
    }

    public double calculateIncomeForPeriod(Date from, Date to) {
        return orderService.calculateIncomeForPerioud(from, to);
    }

    public int getCountPerformedOrder(Date from, Date to) {
        return orderService.getCountPerformedOrder(from, to);
    }

    public List<Book> getStaleBooks(int staleMonths) {
        LocalDate staleDate = LocalDate.now().minusMonths(staleMonths);
        return getAllBooks().stream()
                .filter(book -> isBookStale(book, staleDate))
                .collect(Collectors.toList());
    }

    private boolean isBookStale(Book book, LocalDate staleDate) {
        Date staleDateAsDate = Date.from(staleDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return getOrdersForBook(book.getBookId()).stream()
                .noneMatch(order -> {
                    Date orderDate = order.getOrderDate();
                    return orderDate.after(staleDateAsDate) || orderDate.equals(staleDateAsDate);
                });
    }

    public List<Order> getOrdersForBook(int bookId) {
        return orderService.getAll().stream()
                .filter(order -> order.getBook().getBookId() == bookId)
                .collect(Collectors.toList());
    }


    public void saveStateToJson(String filePath) {
        AppState state = new AppState(
                getAllBooks(),
                getAllOrder(),
                getAllCustomers(),
                getAllRequestBook()
        );
        JsonUtil.saveState(state, filePath);
    }

    public void loadStateFromJson(String filePath) {
        AppState state = JsonUtil.loadState(filePath);
        if (state == null) return;

        for (Book book : state.getBooks()) {
            wareHouseService.add(book);
        }
        for (Customer customer : state.getCustomers()) {
            customerService.add(customer);
        }
        for (RequestBook request : state.getRequests()) {
            requestService.add(request);
        }
        for (Order order : state.getOrders()) {
            orderService.add(order);
        }

        int maxBookId = state.getBooks().stream()
                .mapToInt(Book::getBookId)
                .max()
                .orElse(0);
        Book.setCount(maxBookId + 1);

        int maxCustomerId = state.getCustomers().stream()
                .mapToInt(Customer::getCustomerID)
                .max().orElse(0);
        Customer.setCountId(maxCustomerId + 1);

        int maxOrderId = state.getOrders().stream()
                .mapToInt(Order::getOrderId)
                .max().orElse(0);
        Order.setOrderCount(maxOrderId + 1);

        int maxRequestId = state.getRequests().stream()
                .mapToInt(RequestBook::getId)
                .max().orElse(0);
        RequestBook.setCountId(maxRequestId + 1);

    }
}
