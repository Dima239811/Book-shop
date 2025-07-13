import dependesies.context.ApplicationContext;
import dependesies.factory.BeanFactory;
import enums.OrderStatus;
import enums.StatusBook;
import model.Book;
import model.Customer;
import controller.DataManager;
import ui.MenuController;

import java.util.Date;

public class Main {

    public ApplicationContext run() {
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        return applicationContext;
    }

    public static void main(String[] args) {
        //DataManager dataManager = BeanFactory.getInstance().getBean(DataManager.class);

        Main main = new Main();
        ApplicationContext applicationContext = main.run();

        DataManager dataManager = applicationContext.getBean(DataManager.class);
        dataManager.loadStateFromJson("state.json");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Сохраняем состояние...");
            dataManager.saveStateToJson("state.json");
        }));

        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}
