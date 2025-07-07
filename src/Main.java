import enums.OrderStatus;
import enums.StatusBook;
import model.Book;
import model.Customer;
import controller.DataManager;
import ui.MenuController;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}
