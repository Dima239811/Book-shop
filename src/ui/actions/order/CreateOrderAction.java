package ui.actions.order;

import controller.DataManager;
import model.Book;
import model.Customer;
import ui.actions.IAction;

import java.util.Date;
import java.util.Scanner;

public class CreateOrderAction implements IAction {
    private DataManager dataManager;

    public CreateOrderAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        // создание книги
        Scanner scanner = new Scanner(System.in);
        System.out.println("Чтобы сформировать заказ на книгу она должна быть в базе!");
        System.out.println("Введите id книги для поиска");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            Book book = dataManager.findBook(id);
            if (book!= null) {
                System.out.println("Ваша книга найдена!");
                System.out.println("Введите имя клиента");
                String name = scanner.nextLine();
                System.out.println("Введите возраст клиента");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Введите email клиента");
                String email = scanner.nextLine();
                System.out.println("Введите адресс клиента");
                String address = scanner.nextLine();
                Customer customer = new Customer(name, age, "+79855566", email, address);

                dataManager.createOrder(book, customer, new Date());
            }

        } catch (Exception ex) {
            System.out.println("некоректный id");
        }
    }


    }


