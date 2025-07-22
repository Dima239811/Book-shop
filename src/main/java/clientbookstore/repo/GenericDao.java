package clientbookstore.repo;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    void create(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    T findById(int id) throws SQLException;
}