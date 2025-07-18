package clientbookstore.ui.actions.customer;

import clientbookstore.controller.DataManager;

import clientbookstore.model.entity.Customer;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowAllCustomer implements IAction {
    private static final Logger logger = LoggerFactory.getLogger(ShowAllCustomer.class);
    private DataManager dataManager;

    public ShowAllCustomer(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: вывод всех клиентов");
        System.out.println("Список всех клиентов");
        System.out.println("вывод из клиентов книг");
        List<Customer> customers = dataManager.getAllCustomers();
        System.out.println("\n=== СПИСОК ВСЕХ КЛИЕНТОВ ===");
        System.out.println("Всего клиентов: " + customers.size());
        System.out.println("-----------------------------------------------");

        if (customers.isEmpty()) {
            logger.info("Список клиентов пуст");
            System.out.println("Клиенты не найдены");
        } else {
            customers.forEach(c -> System.out.println(c));
            logger.info("Выведено {} клиентов", customers.size());
        }

        System.out.println("-----------------------------------------------");
    }
}
