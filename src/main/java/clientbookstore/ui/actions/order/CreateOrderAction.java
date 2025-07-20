package clientbookstore.ui.actions.order;

import clientbookstore.controller.MainContr;
import clientbookstore.model.entity.Book;
import clientbookstore.model.entity.Customer;
import clientbookstore.model.entity.Order;
import clientbookstore.ui.actions.IAction;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateOrderAction implements IAction {
    private MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(CreateOrderAction.class);

    public CreateOrderAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {

        logger.info("Начало обработки команды: CreateOrderAction");

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
            System.out.println("Введите номер телефона клиента");
            String number = scanner.nextLine();
            Customer customer = new Customer(name, age, number, email, address);

            Order order = new Order(book, customer, new Date(), book.getPrice());
            dataManager.createOrder(order);

            logger.info("Команда CreateOrderAction выполнена успешно");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка валидации: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } catch (InputMismatchException e) {
            logger.error("Ошибка ввода: {}", e.getMessage());
            System.out.println("Некорректно заполнено поле");
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при выполнении команды: ", e);
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}


