package clientbookstore.repo.dao;

import java.util.List;

public interface GenericDAO<T> {
    void create(T object);
    T findById(int id);
    void update(T object);
    void delete(int id);
    List<T> getAll();
}
