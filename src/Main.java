import clientbookstore.dependesies.context.ApplicationContext;
import clientbookstore.dependesies.factory.BeanFactory;
import clientbookstore.controller.DataManager;
import clientbookstore.ui.Builder;
import org.apache.log4j.BasicConfigurator;
import clientbookstore.ui.MenuController;

public class Main {

    public ApplicationContext run() {
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        return applicationContext;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Main main = new Main();
        ApplicationContext applicationContext = main.run();

        DataManager dataManager = applicationContext.getBean(DataManager.class);
        //System.out.println("DataManager в Main: " + dataManager);

        dataManager.loadStateFromJson("state.json");

        Builder builder = applicationContext.getBean(Builder.class);
        builder.buildMenu();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Сохраняем состояние...");
            dataManager.saveStateToJson("state.json");
        }));

        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}
