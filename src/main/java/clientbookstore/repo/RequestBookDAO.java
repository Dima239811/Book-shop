package clientbookstore.repo;

import clientbookstore.model.entity.RequestBook;
import clientbookstore.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class RequestBookDAO extends HibernateAbstractDao<RequestBook> {
    private static final Logger logger = LoggerFactory.getLogger(RequestBookDAO.class);

    public RequestBookDAO() {
        super(RequestBook.class);
    }

    public RequestBook findByBookId(int id) throws SQLException {
        try (Session session = HibernateUtil.getSession()) {

            String hql = "FROM RequestBook  WHERE bookid = :bookId";
            Query<RequestBook> query = session.createQuery(hql, RequestBook.class);
            query.setParameter("bookId", id);

            RequestBook requestBook = query.uniqueResult();

            if (requestBook != null) {
                return requestBook;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error finding RequestBook with bookId: " + id, e);
            throw new SQLException("Error fetching RequestBook with bookId: " + id, e);
        }
    }

    public List<RequestBook> getAllWithBooksAndCustomers() throws SQLException {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT DISTINCT r FROM RequestBook  r " +
                                    "LEFT JOIN FETCH r.book " +
                                    "LEFT JOIN FETCH r.customer", RequestBook.class)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error getting all book requests with details", e);
            throw new SQLException(e);
        }
    }
}
