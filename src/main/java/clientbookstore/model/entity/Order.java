package clientbookstore.model.entity;

import clientbookstore.model.converters.OrderStatusConverter;
import clientbookstore.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "bookid", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customer;

    @Column(name = "dateorder", nullable = false)
    private Date orderDate;

    @Column(name = "price", nullable = false)
    private double finalPrice;

    @Convert(converter = OrderStatusConverter.class)
    @Column(nullable = false)
    private OrderStatus status;  // создан/ выполнен/ отменен

    public Order(Book book, Customer customer, Date orderDate, double finalPrice) {
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = OrderStatus.NEW;
    }

    public Order() { }

    public Order(Book book, Customer customer, Date orderDate, double finalPrice, OrderStatus status) {
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = status;
    }

    public Order(int orderId, Book book, Customer customer, Date orderDate, double finalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Заказ #" + orderId +
                "\nКнига: " + book.getName() + " (ID: " + book.getBookId() + ")" +
                "\nКлиент: " + customer.getFullName() + " (ID: " + customer.getCustomerID() + ")" +
                "\nДата заказа: " + orderDate +
                "\nСумма: " + String.format("%.2f руб.", finalPrice) +
                "\nСтатус: " + status.getValue();
    }
}
