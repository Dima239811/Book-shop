package ui;

import controller.DataManager;
import ui.action_factory.ActionFactory;
import ui.action_factory.DefaultActionFactory;

import java.util.Scanner;

public class MenuController {
    private static MenuController instance;

    private DataManager dataManager;
    private Builder builder;
    private Navigator navigator;

    private MenuController() {
        this.dataManager = new DataManager();
        ActionFactory actionFactory = new DefaultActionFactory(dataManager);

        this.builder = new Builder(actionFactory);
        this.navigator = new Navigator(builder.getRootMenu());
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            navigator.printMenu();
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            catch(Exception exception) {
                System.out.println("Некорректный ввод, попробуйте снова");
                scanner.nextLine();
                continue;
            }

            if (choice == 0) {
                // Если в корневом меню — выйти, иначе назад
                if (navigator.isEmpty()) {
                    running = false;
                } else {
                    navigator.backToParent();
                }
            } else {
                navigator.navigate(choice);
            }
        }
    }



}
