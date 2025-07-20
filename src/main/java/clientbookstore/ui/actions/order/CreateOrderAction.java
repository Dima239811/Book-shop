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

            // Поиск клиента
            System.out.println("\nКлиент должен быть в базе!");
            System.out.print("Введите ID клиента: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();

            Customer customer = dataManager.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Клиент с ID " + customerId + " не найден");
            }
            System.out.println("Найден клиент: " + customer.getFullName());

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


