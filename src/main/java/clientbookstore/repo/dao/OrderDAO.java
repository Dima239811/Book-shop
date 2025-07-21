package clientbookstore.repo.dao;

import clientbookstore.model.entity.Order;

public class OrderDAO extends HibernateAbstractDao<Order, Integer>{
    public OrderDAO() {
        super(Order.class);
    }
}
