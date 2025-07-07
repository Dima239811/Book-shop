package ui.actions.book;

import controller.DataManager;
import ui.actions.IAction;

import java.util.Scanner;

public class WriteOffBookAction implements IAction {
    private DataManager dataManager;

    public WriteOffBookAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите id списываемой книги: ");

        try {
            int id = scanner.nextInt();
            dataManager.writeOffBook(id);
        } catch (Exception ex) {
            System.out.println("Некорректный ввод");
            return;
        }


    }
}
