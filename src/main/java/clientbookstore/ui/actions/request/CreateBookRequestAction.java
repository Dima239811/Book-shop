package clientbookstore.ui.actions.request;

import clientbookstore.controller.MainContr;
import clientbookstore.model.entity.Book;
import clientbookstore.model.entity.Customer;
import clientbookstore.model.entity.RequestBook;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class CreateBookRequestAction implements IAction {
    private final MainContr dataManager;
    private static final Logger logger = LoggerFactory.getLogger(CreateBookRequestAction.class);

    @Autowired
    public CreateBookRequestAction(MainContr dataManager) {
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
