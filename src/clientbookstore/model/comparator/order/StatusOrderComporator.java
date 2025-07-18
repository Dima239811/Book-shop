package model.comparator.order;

import model.entity.Order;

import java.util.Comparator;

public class StatusOrderComporator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getStatus().compareTo(o2.getStatus());
    }
}
