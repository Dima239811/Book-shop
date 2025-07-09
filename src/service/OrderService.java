package service;

import enums.OrderStatus;
import model.Book;
import model.Customer;
import model.Order;
import collection.OrderCol;

import java.util.Date;
import java.util.List;

public class OrderService implements IService<Order>{
    private final OrderCol orderCol;

    public OrderService() {
        this.orderCol = new OrderCol();
    }

//    public void createOrder(Book book, Customer customer, Date orderDate) {
//        orderCol.addOrder(book, customer, orderDate);
//    }

    public void cancelOrder(int orderId) {
        orderCol.changeStatus(orderId, OrderStatus.CANCELLED);
    }

    public void changeOrderStatus(int orderId, OrderStatus status) {
        orderCol.changeStatus(orderId, status);
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
                return orderCol.getAll();
        }
    }

    public List<Order> sortPerformOrders(String criteria, Date from, Date to) {
        if (criteria.equals("по дате")) {
            return orderCol.sortPerformOrderByDate(from, to);
        } else if (criteria.equals("по цене")) {
            return orderCol.sortPerformOrderByPrice(from, to);
        } else {
            return orderCol.getAll();
        }
    }

    public double calculateIncomeForPerioud(Date from, Date to) {
        return orderCol.calculateIncomeForPerioud(from, to);
    }

    public int getCountPerformedOrder(Date from, Date to) {
        return orderCol.getCountPerformedOrder(from ,to);
    }

    @Override
    public List<Order> getAll() {
        return orderCol.getAll();
    }

    @Override
    public Order getById(int id) {
        return orderCol.findById(id);
    }

    @Override
    public void add(Order item) {
        Order existing = orderCol.findById(item.getOrderId());
        if (existing != null) {
            update(item);
        } else {
            orderCol.add(item);
        }
    }

    @Override
    public void update(Order item) {
        orderCol.update(item);
    }
}
