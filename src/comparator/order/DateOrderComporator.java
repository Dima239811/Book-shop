package comparator.order;

import model.Order;

import java.util.Comparator;
import java.util.Date;

public class DateOrderComporator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getOrderDate().compareTo(o2.getOrderDate());
    }
}
