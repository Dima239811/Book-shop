package clientbookstore.repo.dao;

import clientbookstore.repo.daoold.GenericDAO;
import clientbookstore.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HibernateAbstractDao <T, PK extends Serializable> implements GenericDAO<T> {
    private static final Logger logger = LoggerFactory.getLogger(HibernateAbstractDao.class);
    private final Class<T> type;

    protected HibernateAbstractDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T object) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(object);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            logger.error("Error creating " + type.getName(), e);
            throw e;
        }
    }

    @Override
    public T findById(int id) throws SQLException {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(type, id);
        }
    }

    @Override
    public void update(T object) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(object);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            logger.error("Error updating " + type.getName(), e);
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            T entity = session.get(type, id);
            if (entity != null) {
                session.remove(entity);
                tx.commit();
            } else {
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
            logger.error("Error deleting " + type.getName(), e);
            throw e;
        }

    }

    @Override
    public List<T> getAll() throws SQLException {
        try {
            return HibernateUtil.getSession()
                    .createQuery("FROM " + type.getName(), type)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error getting all " + type.getName(), e);
            return new ArrayList<>();
        }
    }
}
