package clientbookstore;

import clientbookstore.controller.MainContr;
import clientbookstore.ui.Builder;
import clientbookstore.ui.MenuController;
import clientbookstore.util.AppConfig;
import clientbookstore.util.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        MainContr dataManager = context.getBean(MainContr.class);
        Builder builder = context.getBean(Builder.class);
        MenuController menuController = context.getBean(MenuController.class);

        builder.buildMenu();
        menuController.run();

        context.registerShutdownHook();
    }
}
