package clientbookstore.ui.actions.csv.customer;

import clientbookstore.controller.DataManager;
import clientbookstore.exception.DataExportException;
import clientbookstore.ui.actions.IAction;

import java.util.Scanner;

public class ExportCustomerAction implements IAction {
    private final DataManager dataManager;
    private final Scanner scanner;

    public ExportCustomerAction(DataManager dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }
    @Override
    public void execute() {
        try {
            System.out.println("\n=== Экспорт клиентов в CSV ===");
            System.out.print("Введите путь для сохранения файла: ");
            String path = scanner.nextLine().trim();

            dataManager.exportCustomersToCsv(path);
            System.out.println("Экспорт успешно завершен");

        } catch (DataExportException e) {
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
