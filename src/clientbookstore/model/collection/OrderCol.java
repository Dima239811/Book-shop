package clientbookstore.model.collection;



import clientbookstore.model.comparator.order.DateOrderComporator;
import clientbookstore.model.comparator.order.PriceOrderComporator;
import clientbookstore.model.comparator.order.StatusOrderComporator;
import clientbookstore.model.enums.OrderStatus;

import clientbookstore.model.entity.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCol implements ICollection<Order> {
    private List<Order> orderList = new ArrayList<>();

    public void changeStatus(int orderId, OrderStatus status) {
        for (Order ord: orderList) {
            if (ord.getOrderId() == orderId) {
                ord.setStatus(status);
                System.out.println("указанный вами статус /" + status.getValue() + "/ установлен");
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

    public int getCountPerformedOrder(Date from, Date to) {
        return  filterOrdersByDateAndStatus(from , to, OrderStatus.COMPLETED)
                .stream().collect(Collectors.counting()).intValue();
    }

    @Override
    public void add(Order item) {
        orderList.add(item);
    }

    @Override
    public void update(Order customer) {
        Order existing = findById(customer.getOrderId());
        if (existing != null) {
            existing.setBook(customer.getBook());
            existing.setStatus(customer.getStatus());
            existing.setCustomer(customer.getCustomer());
            existing.setOrderDate(customer.getOrderDate());
            existing.setFinalPrice(customer.getFinalPrice());
        }
    }

    @Override
    public Order findById(int id) {
        return orderList.stream()
                .filter(c -> c.getOrderId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return orderList;
    }
}
