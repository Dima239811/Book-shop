package clientbookstore.service.entityService;

import clientbookstore.model.comparator.order.DateOrderComporator;
import clientbookstore.model.comparator.order.PriceOrderComporator;
import clientbookstore.model.comparator.order.StatusOrderComporator;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.model.enums.OrderStatus;

import clientbookstore.model.entity.Order;
import clientbookstore.repo.daoold.OrderDAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IService<Order> {

    @Inject
    private OrderDAO orderDAO;

    public void cancelOrder(int orderId) {
        try {
            Order order = orderDAO.findById(orderId);
            if (order == null) {
                System.out.println("Заказ с id: " + orderId + " не существует");
                return;
            }
            order.setStatus(OrderStatus.CANCELLED);
            try {
                orderDAO.update(order);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to update order status " + orderId + " in orderSevice in cancelOrder()" + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get order by id " + orderId + " in orderSevice in cancelOrder()" + e.getMessage());
        }
    }

    public void changeOrderStatus(int orderId, OrderStatus status) {
        try {
            Order order = orderDAO.findById(orderId);
            if (order == null) {
                System.out.println("Заказ с id: " + orderId + " не существует");
                return;
            }
            order.setStatus(status);
            try {
                orderDAO.update(order);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to update order status " + orderId + " in orderSevice in changeOrderStatus()" + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get order by id " + orderId + " in orderSevice in changeOrderStatus()" + e.getMessage());
        }
    }

    public List<Order> sortOrders(String criteria) {
        try {
            List<Order> orders = orderDAO.getAll();
            switch (criteria.toLowerCase()) {
                case "по дате":
                    orders.sort(new DateOrderComporator());
                    return orders;
                case "по цене":
                    orders.sort(new PriceOrderComporator());
                    return orders;
                case "по статусу":
                    orders.sort(new StatusOrderComporator());
                    return orders;
                default:
                    System.out.println("Ошибка: неопознанный критерий сортировки.");
                    return orders;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail to get all order " +  " in orderSevice in sortOrders()" + e.getMessage());
        }
    }

    private List<Order> filterOrdersByDateAndStatus(Date from, Date to, OrderStatus status) throws SQLException {
        return orderDAO.getAll().stream()
                .filter(order -> order.getStatus() == status)
                .filter(order -> !order.getOrderDate().before(from) && !order.getOrderDate().after(to))
                .collect(Collectors.toList());
    }

    public List<Order> sortPerformOrders(String criteria, Date from, Date to) {
        try {
            List<Order> completedOrders = filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED);
            //System.out.println("выполненные заказы в методе " + completedOrders);
            switch (criteria.toLowerCase()) {
                case "по дате":
                    completedOrders.sort(new DateOrderComporator());
                    return completedOrders;
                case "по цене":
                    completedOrders.sort(new PriceOrderComporator());
                    return completedOrders;
                default:
                    return completedOrders;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail sortPerformOrders " +  " in orderSevice in sortPerformOrders()" + e.getMessage());
        }
    }

    public double calculateIncomeForPerioud(Date from, Date to) {
        try {
            return filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED)
                    .stream()
                    .mapToDouble(Order::getFinalPrice)
                    .sum();
        } catch (SQLException e) {
            throw new RuntimeException("Fail calculateIncomeForPerioud " +  " in orderSevice in calculateIncomeForPerioud()" + e.getMessage());
        }
    }

    public int getCountPerformedOrder(Date from, Date to) {
        try {
            return filterOrdersByDateAndStatus(from, to, OrderStatus.COMPLETED).size();
        } catch (SQLException e) {
            throw new RuntimeException("Fail getCountPerformedOrder " +  " in orderSevice in getCountPerformedOrder()" + e.getMessage());
        }
    }

    @Override
    public List<Order> getAll() {
        try {
            return orderDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Fail getAll " +  " in orderSevice in getAll()" + e.getMessage());
        }
    }

    @Override
    public Order getById(int id) {
        try {
            return orderDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Fail getById " +  " in orderSevice in getById()" + e.getMessage());
        }
    }

    @Override
    public void add(Order item) {
        try {
            Order existing = orderDAO.findById(item.getOrderId());
            if (existing != null) {
                update(item);
            } else {
                orderDAO.create(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fail add " +  " in orderSevice in add()" + e.getMessage());
        }
    }

    @Override
    public void update(Order item) {
        try {
            orderDAO.update(item);
        } catch (SQLException e) {
            throw new RuntimeException("Fail update " +  " in orderSevice in update()" + e.getMessage());
        }
    }
}
