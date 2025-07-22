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
public class ShowAllOrdersAction implements IAction {
    private final MainContr dataManager;

    private static final Logger logger = LoggerFactory.getLogger(ShowAllOrdersAction.class);

    @Autowired
    public ShowAllOrdersAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: просмотр  всех заказов");
        System.out.println("Список всех заказов");
        System.out.println("вывод из списка заказов");
        List<Order> oders = dataManager.getAllOrder();
        System.out.println("\n=== СПИСОК ВСЕХ заказов ===");
        System.out.println("Всего заказов: " + oders.size());
        System.out.println("-----------------------------------------------");

        if (oders.isEmpty()) {
            logger.info("список заказов пуст");
            System.out.println("заказы не найдены");
        } else {
            oders.forEach(order -> System.out.println(order));
            logger.info("выведено {} заказов", oders.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
