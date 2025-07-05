package model;



import comparator.request.LetterRequestComporator;
import enums.RequestStatus;
import enums.StatusBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
