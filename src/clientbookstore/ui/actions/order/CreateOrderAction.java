package clientbookstore.ui.actions.order;

import clientbookstore.controller.DataManager;
import clientbookstore.model.Book;
import clientbookstore.model.Customer;
import clientbookstore.model.Order;
import clientbookstore.ui.actions.IAction;

import java.util.Date;
import java.util.InputMismatchException;
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

            if (book == null) {
                throw new IllegalArgumentException("Книга с ID " + id + " не найдена");
            }

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

            Order order = new Order(book, customer, new Date(), book.getPrice());
            dataManager.createOrder(order);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Некорректно заполнено поле");
        }
        catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }


    }


