package model;



import comparator.book.YearComporator;
import comparator.order.DateOrderComporator;
import comparator.order.PriceOrderComporator;
import comparator.order.StatusOrderComporator;
import enums.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCol {
    private List<Order> orderList;
    private int orderId = 0;

    public OrderCol() {
        this.orderList = new ArrayList<>();
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void addOrder(Book book, Customer customer, Date orderDate) {
        Order order = new Order(orderId, book, customer, orderDate, book.getPrice());

        if (findOrder(order.getOrderId()) != null) {
            updateOrder(order);
            return;
        }

        orderList.add(order);
        orderId ++;
        System.out.println("заказ создан");
    }

    public void addOrder(Order order) {
        if (findOrder(order.getOrderId()) != null) {
            updateOrder(order);
            return;
        }

        orderList.add(order);
    }

    public void updateOrder(Order order) {
        Order existing = findOrder(order.getOrderId());
        if (existing != null) {
            existing.setStatus(order.getStatus());
            existing.setBook(order.getBook());
            existing.setCustomer(order.getCustomer());
            existing.setOrderDate(order.getOrderDate());
            existing.setFinalPrice(order.getFinalPrice());
            return;
        }
    }


    public Order findOrder(int id) {
        for (Order b: orderList) {
            if (b.getOrderId() == id) {
                return b;
            }
        }
        return null;
    }

    public void changeStatus(int orderId, OrderStatus status) {
        for (Order ord: orderList) {
            if (ord.getOrderId() == orderId) {
                ord.setStatus(status);
                System.out.println("указанный вами статус " + status + " установлен");
                return;
            }
        }
    }

    public List<Order> sortByDate() {
        orderList.sort(new DateOrderComporator());
        return orderList;
    }

    public List<Order> sortByPrice() {
        orderList.sort(new PriceOrderComporator());
        return orderList;
    }

    public List<Order> sortByStatus() {
        orderList.sort(new StatusOrderComporator());
        return orderList;
    }

    private List<Order> filterOrdersByDateAndStatus(Date from, Date to, OrderStatus status) {
        return orderList.stream()
                .filter(order -> order.getStatus().equals(status))
                .filter(order -> !order.getOrderDate().before(from) && !order.getOrderDate().after(to))
                .collect(Collectors.toList());
    }

//    public List<Order> sortPerformOrderByDate(Date from, Date to) {
//        var requestByPerformStatus = orderList.stream()
//                .filter(order -> order.getStatus().equals(OrderStatus.COMPLETED))
//                .filter(order -> !order.getOrderDate().before(from) && !order.getOrderDate().after(to))
//                .sorted(new DateOrderComporator()).toList();
//
//        return requestByPerformStatus;
//    }

    public List<Order> sortPerformOrderByDate(Date from, Date to) {
        return filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED)
                .stream()
                .sorted(new DateOrderComporator())
                .collect(Collectors.toList());
    }

    public List<Order> sortPerformOrderByPrice(Date from, Date to) {
        return filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED)
                .stream()
                .sorted(new PriceOrderComporator())
                .collect(Collectors.toList());


//        public List<Order> sortPerformOrderByPrice(Date from, Date to) {
//        var requestByPerformPrice = orderList.stream()
//                .filter(order -> order.getStatus().equals(OrderStatus.COMPLETED))
//                .filter(order -> !order.getOrderDate().before(from) && !order.getOrderDate().after(to))
//                .sorted(new PriceOrderComporator()).toList();
//
//        return requestByPerformPrice;
    }

    public double calculateIncomeForPerioud(Date from, Date to) {
        return filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED)
                .stream()
                .mapToDouble(Order::getFinalPrice)
                .sum();
    }
}
