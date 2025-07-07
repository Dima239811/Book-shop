package ui.actions;

import javax.swing.*;
import java.util.Scanner;

public class AddBookAction implements IAction {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название книги: ");
        String name = scanner.nextLine();
        System.out.print("Введите автора: ");
        String author = scanner.nextLine();
        System.out.print("Введите год издания книги: ");
        int year = scanner.nextInt();
        System.out.print("Введите стоимость книги: ");
        double price = scanner.nextDouble();


        // ВЫЗВАТЬ КАК-ТО ДОБАВЛЕНИЕ КНИГИ
        System.out.println("Книга '" + name + "' автора '" + author + "' добавлена.");
    }
}
