//package clientbookstore.model.collection;
//
//
//
//import clientbookstore.model.comparator.request.LetterRequestComporator;
//import clientbookstore.model.enums.RequestStatus;
//import clientbookstore.model.enums.StatusBook;
//import clientbookstore.model.entity.Book;
//import clientbookstore.model.entity.RequestBook;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class RequestBookCol implements ICollection<RequestBook> {
//    private List<RequestBook> requests = new ArrayList<>();
//
//    public void closeRequest(Book book) {
//        RequestBook requestBook = findById(book.getBookId());
//            if (requestBook != null) {
//                requestBook.setStatus(RequestStatus.CLOSED);
//                requestBook.setStatusBook(StatusBook.IN_STOCK);
//                System.out.println("запрос на книгу закрыт");
//            }
//        }
//
//    public List<RequestBook> sortByLetter() {
//        requests.sort(new LetterRequestComporator());
//        return requests;
//    }
//
//    public List<RequestBook> sortByCountRequest() {
//        var requestByBooks = requests.stream().collect(Collectors.groupingBy(RequestBook::getBook, Collectors.counting()));
//
//        // по возрастанию запросов
//        return requests.stream().sorted((o1, o2) -> {
//            long countO1 = requestByBooks.get(o1.getBook());
//            long countO2 = requestByBooks.get(o2.getBook());
//            return Long.compare(countO1, countO2);
//        }).collect(Collectors.toList()) ;
//    }
//
//    @Override
//    public void add(RequestBook item) {
//        requests.add(item);
//    }
//
//    @Override
//    public void update(RequestBook requestBook) {
//        RequestBook existing = findById(requestBook.getId());
//        if (existing != null) {
//            existing.setStatus(requestBook.getStatus());
//            existing.setBook(requestBook.getBook());
//            existing.setCustomer(requestBook.getCustomer());
//        }
//    }
//
//    @Override
//    public RequestBook findById(int id) {
//        return requests.stream()
//                .filter(c -> c.getId() == id)
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public List<RequestBook> getAll() {
//        return requests;
//    }
//}
