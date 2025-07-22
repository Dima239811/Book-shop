package clientbookstore.repo;

import clientbookstore.model.entity.Book;
import clientbookstore.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class BookDAO extends HibernateAbstractDao<Book> {

    public BookDAO() {
        super(Book.class);
        System.out.println("вызван bookDAO");
    }

    public boolean existsByNameAndAuthor(String name, String author) throws SQLException {
        try (Session session = HibernateUtil.getSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(b) FROM Book b WHERE LOWER(b.name) = LOWER(:name) " +
                                    "AND LOWER(b.author) = LOWER(:author)", Long.class)
                    .setParameter("name", name)
                    .setParameter("author", author)
                    .getSingleResult();
            return count > 0;
        }
    }
}
