package clientbookstore.ui.actions.order;

import clientbookstore.controller.MainContr;

import clientbookstore.model.entity.Order;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortOrdersByDateAction implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(SortOrdersByDateAction.class);

    @Autowired
    public SortOrdersByDateAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: сортировка всех заказов по дате");
        System.out.println("Сортировка по дате: ");
        List<Order> orders = dataManager.sortOrders("по дате");

        if (orders.isEmpty()) {
            logger.info("список заказов пуст");
            System.out.println("заказы не найдены");
        } else {
            orders.forEach(book -> System.out.println(book));
            logger.info("выведено {} заказов", orders.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
