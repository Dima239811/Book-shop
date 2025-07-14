import clientbookstore.dependesies.context.ApplicationContext;
import clientbookstore.dependesies.factory.BeanFactory;
import clientbookstore.controller.DataManager;
import org.apache.log4j.BasicConfigurator;
import clientbookstore.ui.MenuController;

public class Main {

    public ApplicationContext run() {
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        DataManager dataManager = applicationContext.getBean(DataManager.class);
        MenuController menuController = new MenuController(dataManager);

        applicationContext.getBeanMap().put(DataManager.class, dataManager);
        applicationContext.getBeanMap().put(MenuController.class, menuController);

        return applicationContext;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Main main = new Main();
        ApplicationContext applicationContext = main.run();

        DataManager dataManager = applicationContext.getBean(DataManager.class);
        dataManager.loadStateFromJson("state.json");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Сохраняем состояние...");
            dataManager.saveStateToJson("state.json");
        }));

        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}
