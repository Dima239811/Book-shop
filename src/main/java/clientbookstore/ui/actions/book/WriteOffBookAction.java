package clientbookstore.ui.actions.book;

import clientbookstore.controller.MainContr;
import clientbookstore.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WriteOffBookAction implements IAction {
    private final MainContr dataManager;

    private static final Logger logger = LoggerFactory.getLogger(WriteOffBookAction.class);

    @Autowired
    public WriteOffBookAction(MainContr dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void execute() {
        logger.info("пользователь выбрал списание книги");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите id списываемой книги: ");

        try {
            int id = scanner.nextInt();
            dataManager.writeOffBook(id);
            logger.info("книга с id = {} успешно списана со склада", id);
        } catch (Exception ex) {
            logger.error("Некоректный ввод id, не введено целое число");
            System.out.println("Некорректный ввод, введите целое число");
        }
    }
}
