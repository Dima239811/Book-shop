package clientbookstore.ui;

import clientbookstore.controller.DataManager;

import clientbookstore.ui.action_factory.ActionFactory;
import clientbookstore.ui.action_factory.DefaultActionFactory;

import java.util.Scanner;

public class MenuController {

    private DataManager dataManager;
    private Builder builder;
    private Navigator navigator;

    public MenuController(DataManager dataManager) {
        ActionFactory actionFactory = new DefaultActionFactory(dataManager);

        this.builder = new Builder(actionFactory);
        this.navigator = new Navigator(builder.getRootMenu());
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
