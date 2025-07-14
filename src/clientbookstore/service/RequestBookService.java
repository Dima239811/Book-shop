package clientbookstore.service;

import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.model.Book;
import clientbookstore.model.Customer;
import clientbookstore.model.RequestBook;
import clientbookstore.collection.RequestBookCol;

import java.util.List;

public class RequestBookService implements IService<RequestBook>{

    @Inject
    private RequestBookCol requestBookCol;

    public void closeRequest(Book book) {
        requestBookCol.closeRequest(book);
    }

    public void createRequest(Book book, Customer customer) {
        RequestBook requestBook = new RequestBook(customer, book);
        requestBookCol.add(requestBook);
    }

    public List<RequestBook> sortRequest(String criteria) {
        if (criteria.equals("по алфавиту")) {
            return requestBookCol.sortByLetter();
        } else if (criteria.equals("по количеству запросов")) {
            return requestBookCol.sortByCountRequest();
        } else {
            System.out.println("такого критерия сортировки нет");
            return requestBookCol.getAll();
        }
    }

    @Override
    public List<RequestBook> getAll() {
        return requestBookCol.getAll();
    }

    @Override
    public RequestBook getById(int id) {
        return requestBookCol.findById(id);
    }

    @Override
    public void add(RequestBook item) {
        RequestBook existing = requestBookCol.findById(item.getId());
        if (existing != null) {
            update(item);
        } else {
            requestBookCol.add(item);
        }
    }

    @Override
    public void update(RequestBook item) {
        requestBookCol.update(item);
    }
}
