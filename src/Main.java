import enums.OrderStatus;
import enums.StatusBook;
import model.Book;
import model.Customer;
import controller.DataManager;
import ui.MenuController;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
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
