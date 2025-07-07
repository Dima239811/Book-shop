package ui.actions.book;

import controller.DataManager;
import model.Book;
import ui.actions.IAction;

import java.util.Scanner;

public class AddBookAction implements IAction {

    private DataManager dataManager;

    public AddBookAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

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

        Book book = new Book(name, author, year, price);

        dataManager.addBookToWareHouse(book);
        System.out.println("Книга '" + name + "' автора '" + author + "' добавлена.");
    }
}
