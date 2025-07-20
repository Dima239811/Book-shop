package clientbookstore.repo.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    PK save(T entity);

    void update(T entity);

    void delete(PK id);

    List<T> findAll();

    T find(PK id);
}