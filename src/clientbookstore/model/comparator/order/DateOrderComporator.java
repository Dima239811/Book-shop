package clientbookstore.model.comparator.order;

import clientbookstore.model.entity.Order;

import java.util.Comparator;

public class DateOrderComporator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getOrderDate().compareTo(o2.getOrderDate());
    }
}
