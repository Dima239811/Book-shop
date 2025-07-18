package model.comparator.request;

import model.comparator.book.LetterComporator;
import model.entity.RequestBook;

import java.util.Comparator;

public class LetterRequestComporator implements Comparator<RequestBook> {

    private final LetterComporator letterComporator = new LetterComporator();
    @Override
    public int compare(RequestBook o1, RequestBook o2) {
        return letterComporator.compare(o1.getBook(), o2.getBook());
    }
}
