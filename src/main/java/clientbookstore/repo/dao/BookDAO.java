package clientbookstore.repo.dao;

import clientbookstore.model.entity.Book;

public class BookDAO extends HibernateAbstractDao<Book, Integer> {

    public BookDAO() {
        super(Book.class);
        System.out.println("вызван bookDAO");
    }
}
