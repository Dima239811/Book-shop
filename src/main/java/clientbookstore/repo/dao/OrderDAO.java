package clientbookstore.repo.dao;

import clientbookstore.model.entity.Order;
import clientbookstore.util.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class OrderDAO extends HibernateAbstractDao<Order> {
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);
    public OrderDAO() {
        super(Order.class);
    }

    public List<Order> getAllWithBooksAndCustomers() throws SQLException {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.book " +
                                    "LEFT JOIN FETCH o.customer", Order.class)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error getting orders with books and customers", e);
            throw new SQLException(e);
        }
    }
}
