package clientbookstore.controller;

import clientbookstore.model.entity.Book;
import clientbookstore.model.entity.Customer;
import clientbookstore.model.entity.Order;
import clientbookstore.model.entity.RequestBook;
import clientbookstore.service.csv.ICsvService;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.dependesies.annotation.PostConstruct;
import clientbookstore.dependesies.annotation.Qualifier;
import clientbookstore.model.enums.OrderStatus;


import clientbookstore.repo.util.DBConnection;
import clientbookstore.service.entityService.CustomerService;
import clientbookstore.service.entityService.OrderService;
import clientbookstore.service.entityService.RequestBookService;
import clientbookstore.service.entityService.WareHouseService;
import clientbookstore.util.AppConfig;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    @Inject
    private WareHouseService wareHouseService;

    @Inject
    private OrderService orderService;

    @Inject
    private CustomerService customerService;

    @Inject
    private RequestBookService requestService;

    @Inject
    @Qualifier("bookCsvService")
    private ICsvService<Book> bookCsvService;

    @Inject
    @Qualifier("orderCsvService")
    private ICsvService<Order> orderCsvService;

    @Inject
    @Qualifier("customerCsvService")
    private ICsvService<Customer> customerCsvService;

    @Inject
    @Qualifier("requestBookCsvService")
    private ICsvService<RequestBook> requestBookCsvService;

    @PostConstruct
    public void postConstruct() {
        System.out.println("Дата менеджер создался!");
    }

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
        try {
            DBConnection.getInstance().beginTransaction();

            List<Book> imported = bookCsvService.importFromCsv(filePath);
            for (Book b : imported) {
                wareHouseService.add(b);
            }

            DBConnection.getInstance().commit();
            return imported;
        } catch (Exception e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("fail in data manager importBooksFromCsv " + e.getMessage());
        }
//        List<Book> imported = bookCsvService.importFromCsv(filePath);
//        for (Book b : imported) {
//            wareHouseService.add(b);
//        }
//        return imported;
    }

    public void createOrder(Order order) {
        try {
            DBConnection.getInstance().beginTransaction();

            //не нужно так как мы всегда берем в заказ книги которые есть уже в базе
            //wareHouseService.add(order.getBook());
            customerService.add(order.getCustomer());

            orderService.add(order);

            DBConnection.getInstance().commit();

        } catch (SQLException e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("Ошибка при создании заказа: " + e.getMessage(), e);
        }
//        wareHouseService.add(order.getBook());
//        customerService.add(order.getCustomer());
//        orderService.add(order);
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
        try {
            DBConnection.getInstance().beginTransaction();

            List<Order> imported = orderCsvService.importFromCsv(filePath);
            for (Order b : imported) {
                orderService.add(b);
                wareHouseService.add(b.getBook());
                customerService.add(b.getCustomer());
            }

            DBConnection.getInstance().commit();
            return imported;
        } catch (Exception e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("fail in data manager importOrdersFromCsv " + e.getMessage());
        }
//        List<Order> imported = orderCsvService.importFromCsv(filePath);
//        for (Order b : imported) {
//            orderService.add(b);
//            wareHouseService.add(b.getBook());
//            customerService.add(b.getCustomer());
//        }
//        return imported;
    }

    public void addBookToWareHouse(Book book) {
        try {
            DBConnection.getInstance().beginTransaction();

            wareHouseService.add(book);

            if (AppConfig.isAutoCloseRequestsEnabled()) {
                requestService.closeRequest(book);
            }

            DBConnection.getInstance().commit();
        } catch (Exception e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("Ошибка при добавлении книги на склад: " + e.getMessage(), e);
        }
//        wareHouseService.add(book);
//
//        if (AppConfig.isAutoCloseRequestsEnabled()) {
//            requestService.closeRequest(book);
//        }
    }

    public void addRequest(RequestBook requestBook) {
        try {
            DBConnection.getInstance().beginTransaction();

            //не нужно так как мы всегда берем в заказ книги которые есть уже в базе
            //wareHouseService.add(requestBook.getBook());

            customerService.add(requestBook.getCustomer());
            requestService.add(requestBook);

            DBConnection.getInstance().commit();
        } catch (Exception e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("Ошибка при добавлении заявки: " + e.getMessage(), e);
        }
//        wareHouseService.add(requestBook.getBook());
//        customerService.add(requestBook.getCustomer());
//        requestService.add(requestBook);
    }

    public void exportRequestToCsv(String filePath) throws Exception {
        List<RequestBook> requestBooks = getAllRequestBook();
        requestBookCsvService.exportToCsv(requestBooks, filePath);
    }

    public List<RequestBook> importRequestFromCsv(String filePath) throws Exception {
        try {
            DBConnection.getInstance().beginTransaction();

            List<RequestBook> imported = requestBookCsvService.importFromCsv(filePath);
            for (RequestBook b : imported) {
                requestService.add(b);
                wareHouseService.add(b.getBook());
                customerService.add(b.getCustomer());
            }

            DBConnection.getInstance().commit();
            return imported;
        } catch (Exception e) {
            DBConnection.getInstance().rollback();
            throw new RuntimeException("fail in data manager importRequestFromCsv " + e.getMessage());
        }
//        List<RequestBook> imported = requestBookCsvService.importFromCsv(filePath);
//        for (RequestBook b : imported) {
//            requestService.add(b);
//            wareHouseService.add(b.getBook());
//            customerService.add(b.getCustomer());
//        }
//        return imported;
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


//    public void saveStateToJson(String filePath) {
//        AppState state = new AppState(
//                getAllBooks(),
//                getAllOrder(),
//                getAllCustomers(),
//                getAllRequestBook()
//        );
//        JsonUtil.saveState(state, filePath);
//    }
//
//    public void loadStateFromJson(String filePath) {
//        AppState state = JsonUtil.loadState(filePath);
//        if (state == null) return;
//
//        // Загрузка книг
//        int booksLoaded = state.getBooks().size();
//        for (Book book : state.getBooks()) {
//            wareHouseService.add(book);
//        }
//        System.out.println("Загружено книг: " + booksLoaded);
//
//        // Загрузка клиентов
//        int customersLoaded = state.getCustomers().size();
//        for (Customer customer : state.getCustomers()) {
//            customerService.add(customer);
//        }
//        System.out.println("Загружено клиентов: " + customersLoaded);
//
//        // Загрузка запросов
//        int requestsLoaded = state.getRequests().size();
//        for (RequestBook request : state.getRequests()) {
//            requestService.add(request);
//        }
//        System.out.println("Загружено запросов: " + requestsLoaded);
//
//        // Загрузка заказов
//        int ordersLoaded = state.getOrders().size();
//        for (Order order : state.getOrders()) {
//            orderService.add(order);
//        }
//        System.out.println("Загружено заказов: " + ordersLoaded);
//
//
//        int maxBookId = state.getBooks().stream()
//                .mapToInt(Book::getBookId)
//                .max()
//                .orElse(0);
//        Book.setCount(maxBookId + 1);
//
//        int maxCustomerId = state.getCustomers().stream()
//                .mapToInt(Customer::getCustomerID)
//                .max().orElse(0);
//        Customer.setCountId(maxCustomerId + 1);
//
//        int maxOrderId = state.getOrders().stream()
//                .mapToInt(Order::getOrderId)
//                .max().orElse(0);
//        Order.setOrderCount(maxOrderId + 1);
//
//        int maxRequestId = state.getRequests().stream()
//                .mapToInt(RequestBook::getId)
//                .max().orElse(0);
//        RequestBook.setCountId(maxRequestId + 1);
//
//    }
}
