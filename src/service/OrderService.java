package service;

import enums.OrderStatus;
import model.Book;
import model.Customer;
import model.Order;
import model.OrderCol;

import java.util.Date;
import java.util.List;

public class OrderService {
    private final OrderCol orderCol;

    public OrderService() {
        this.orderCol = new OrderCol();
    }

    public void createOrder(Book book, Customer customer, Date orderDate) {
        orderCol.addOrder(book, customer, orderDate);
    }

    public void cancelOrder(int orderId) {
        orderCol.changeStatus(orderId, OrderStatus.CANCELLED);
    }

    public void changeOrderStatus(int orderId, OrderStatus status) {
        orderCol.changeStatus(orderId, status);
    }

    public OrderCol getOrderCol() {
        return orderCol;
    }

    public List<Order> sortOrders(String criteria) {
        switch (criteria.toLowerCase()) {
            case "по дате":
                return orderCol.sortByDate();
            case "по цене":
                return orderCol.sortByPrice();
            case "по статусу":
                return orderCol.sortByStatus();
            default:
                System.out.println("Ошибка: неопознанный критерий сортировки.");
                return orderCol.getOrderList();
        }
    }

    public List<Order> getAllOrder() {
        return orderCol.getOrderList();
    }

    public List<Order> sortPerformOrders(String criteria, Date from, Date to) {
        if (criteria.equals("по дате")) {
            return orderCol.sortPerformOrderByDate(from, to);
        } else if (criteria.equals("по цене")) {
            return orderCol.sortPerformOrderByPrice(from, to);
        } else {
            return orderCol.getOrderList();
        }
    }

    public double calculateIncomeForPerioud(Date from, Date to) {
        return orderCol.calculateIncomeForPerioud(from, to);
    }

    public void addOrder(Order order) {
        orderCol.addOrder(order);
    }

    public Order findOrder(int id) {
        return orderCol.findOrder(id);
    }


}
