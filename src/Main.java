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

        Main main = new Main();
        ApplicationContext context = main.run();

        //DataManager dataManager = context.getBean(DataManager.class);
        DataManager dataManager = DataManager.getInstance();

        dataManager.loadStateFromJson("state.json");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Сохраняем состояние...");
            dataManager.saveStateToJson("state.json");
        }));

        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}
