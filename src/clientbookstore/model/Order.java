package clientbookstore.model;



import clientbookstore.enums.OrderStatus;

import java.util.Date;

public class Order {
    private int orderId;
    private Book book;
    private Customer customer;
    private Date orderDate;
    private double finalPrice;
    private OrderStatus status;  // создан/ выполнен/ отменен
    private static int orderCount = 0;

    public Order(Book book, Customer customer, Date orderDate, double finalPrice) {
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = OrderStatus.NEW;
        this.orderId = orderCount;
        orderCount++;
    }

    public Order() {}

    public Order(int orderId, Book book, Customer customer, Date orderDate, double finalPrice) {
        this.orderId = orderId;
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = OrderStatus.NEW;
        orderCount++;
    }

    public Order(int orderId, Book book, Customer customer, Date orderDate, double finalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.book = book;
        this.customer = customer;
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.status = status;
        orderCount++;
    }

    public static void setOrderCount(int value) {
        orderCount = value;
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
