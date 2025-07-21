package clientbookstore;

import clientbookstore.controller.MainContr;
import clientbookstore.dependesies.context.ApplicationContext;
import clientbookstore.dependesies.factory.BeanFactory;
import clientbookstore.ui.Builder;
import clientbookstore.ui.MenuController;

public class Main {

    public ApplicationContext run() {
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        return applicationContext;
    }

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        Main main = new Main();
        ApplicationContext applicationContext = main.run();

        MainContr dataManager = applicationContext.getBean(MainContr.class);

        Builder builder = applicationContext.getBean(Builder.class);
        builder.buildMenu();

        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}
