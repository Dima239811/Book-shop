package clientbookstore.controller;

import clientbookstore.model.entity.Book;
import clientbookstore.model.entity.Customer;
import clientbookstore.model.entity.Order;
import clientbookstore.model.entity.RequestBook;
import clientbookstore.model.enums.OrderStatus;
import clientbookstore.service.csv.ICsvService;
import clientbookstore.service.entityService.CustomerService;
import clientbookstore.service.entityService.OrderService;
import clientbookstore.service.entityService.RequestBookService;
import clientbookstore.service.entityService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainContr {
    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RequestBookService requestService;

    @Autowired
    @Qualifier("bookCsvService")
    private ICsvService<Book> bookCsvService;

    @Autowired
    @Qualifier("orderCsvService")
    private ICsvService<Order> orderCsvService;

    @Autowired
    @Qualifier("customerCsvService")
    private ICsvService<Customer> customerCsvService;

    @Autowired
    @Qualifier("requestBookCsvService")
    private ICsvService<RequestBook> requestBookCsvService;

    public void writeOffBook(int bookId) {
        bookService.writeOffBook(bookId);
    }

    public Book findBook(int id) {
        return bookService.getById(id);
    }

    public void exportBooksToCsv(String filePath) throws Exception {
        List<Book> books = getAllBooks();
        System.out.println("передано на экспорт " + books.size() + " книг");
        bookCsvService.exportToCsv(books, filePath);
    }

    public List<Book> importBooksFromCsv(String filePath) throws Exception {
        List<Book> imported = bookCsvService.importFromCsv(filePath);
        for (Book b : imported) {
            bookService.add(b);
        }
        return imported;
    }

    public void createOrder(Order order) {
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
            bookService.add(b.getBook());
            customerService.add(b.getCustomer());
        }
        return imported;
    }

    public void addBookToWareHouse(Book book) {
        bookService.add(book);
    }

    public void addRequest(RequestBook requestBook) {
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
            bookService.add(b.getBook());
            customerService.add(b.getCustomer());
        }
        return imported;
    }

    public List<Book> sortBooks(String criteria) {
        return bookService.sortBooks(criteria);
    }

    public List<Book> getAllBooks() {
        return bookService.getAll();
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

    public void addCustomer(Customer customer) {
        customerService.add(customer);
    }

    public Customer getCustomerById(int id) {
        return customerService.getById(id);
    }
}
