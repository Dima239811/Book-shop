package ui.actions.request;

import controller.DataManager;
import model.entity.Book;
import model.entity.Customer;
import model.entity.RequestBook;
import ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CreateBookRequestAction implements IAction {
    private DataManager dataManager;
    private static final Logger logger = LoggerFactory.getLogger(CreateBookRequestAction.class);

    public CreateBookRequestAction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("Пользователь выбрал команду: создание запроса на книгу");

        // создание книги
        Scanner scanner = new Scanner(System.in);
        System.out.println("Чтобы сформировать запрос на книгу она должна быть в базе!");
        System.out.println("Введите id книги для поиска");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            Book book = dataManager.findBook(id);

            if (book == null) {
                logger.error("книга с {} не найдена в базе", id);
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
            String phone = scanner.nextLine();
            Customer customer = new Customer(name, age, phone, email, address);

            RequestBook requestBook = new RequestBook(customer, book);

            dataManager.addRequest(requestBook);
            logger.info("запрос на книгу успешно создан");
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
