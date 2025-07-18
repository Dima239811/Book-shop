package clientbookstore.ui.actions.request;

import clientbookstore.controller.DataManager;
import clientbookstore.model.entity.RequestBook;
import clientbookstore.ui.actions.IAction;

import java.util.List;

public class SortRequestsByTitleAction implements IAction {
    private DataManager dataManager;
    public SortRequestsByTitleAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void execute() {
        System.out.println("Сортировка по алфавиту: ");
        List<RequestBook> requestBooks = dataManager.sortRequest("по алфавиту");

        if (requestBooks.isEmpty()) {
            System.out.println("заказы не найдены");
        } else {
            requestBooks.forEach(book -> System.out.println(book));
        }

        System.out.println("-----------------------------------------------");
    }
}
