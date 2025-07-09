package collection;



import comparator.request.LetterRequestComporator;
import enums.RequestStatus;
import enums.StatusBook;
import model.Book;
import model.Customer;
import model.RequestBook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestBookCol {
    private List<RequestBook> requests = new ArrayList<>();

    public List<RequestBook> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestBook> requests) {
        this.requests = requests;
    }

    public void addRequest(Customer customer, Book book) {
        requests.add(new RequestBook(customer,book));
        System.out.println("Запрос на книгу оставлен.");
    }

    public void addRequest(RequestBook requestBook) {
        if (findRequest(requestBook.getId()) != null) {
            updateRequest(requestBook);
            return;
        }

        requests.add(requestBook);
    }

    public void updateRequest(RequestBook requestBook) {
        RequestBook existing = findRequest(requestBook.getId());
        if (existing != null) {
            existing.setStatus(requestBook.getStatus());
            existing.setBook(requestBook.getBook());
            existing.setCustomer(requestBook.getCustomer());
            return;
        }
    }

    public RequestBook findRequest(int id) {
        for (RequestBook b: requests) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public void closeRequest(Book book) {
        for (RequestBook requestBook: requests) {
            if (book.getAuthtor().equals(requestBook.getBook().getAuthtor()) & book.getName().equals(requestBook.getBook().getName())) {
                requestBook.setStatus(RequestStatus.CLOSED);
                requestBook.setStatusBook(StatusBook.IN_STOCK);
                System.out.println("запрос на книгу закрыт");
            }
        }
    }

    public void createRequest(Book book, Customer customer) {
        requests.add(new RequestBook(customer, book));
        System.out.println("Запрос на книгу создан");
    }

    public List<RequestBook> sortByLetter() {
        requests.sort(new LetterRequestComporator());
        return requests;
    }

    public List<RequestBook> sortByCountRequest() {
        var requestByBooks = requests.stream().collect(Collectors.groupingBy(RequestBook::getBook, Collectors.counting()));
//        System.out.println("в методе группировка");
//        System.out.println(requestByBooks);


        // по возрастанию запросов
        return requests.stream().sorted((o1, o2) -> {
            long countO1 = requestByBooks.get(o1.getBook());
            long countO2 = requestByBooks.get(o2.getBook());
            return Long.compare(countO1, countO2);
        }).collect(Collectors.toList()) ;
    }

}
