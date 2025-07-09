package csv;

import enums.StatusBook;
import exception.DataExportException;
import exception.DataImportException;
import exception.DataValidationException;
import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BookCsvService implements CsvService<Book>{
    @Override
    public void exportToCsv(List<Book> items, String filePath) throws DataExportException {
        if (items == null) {
            throw new DataExportException("Список книг не может быть пустым!");
        }
        File file = new File(filePath.replace("\"", ""));

        //System.out.println("[DEBUG] Начало экспорта " + items.size() + " книг");

        try (PrintWriter printWriter = new PrintWriter(file, "UTF-8")) {

            //System.out.println("[DEBUG] Начало экспорта " + items.size() + " книг");
            file.getParentFile().mkdirs();
            printWriter.println("id,name,author,year,price,status");


            for (Book b: items) {
                if (b == null) {
                    System.out.println("[WARN] Обнаружена null-книга");
                    continue;
                }

                printWriter.println(String.format(Locale.US, "%d,\"%s\",\"%s\",%d,%.2f,%s",
                        b.getBookId(),
                        b.getName().replace("\"", "\"\""),
                        b.getAuthtor().replace("\"", "\"\""),
                        b.getYear(),
                        b.getPrice(),
                        b.getStatus().getValue()
                ));
            }

            printWriter.flush();

        } catch (IOException exception) {
            throw new DataExportException("Файл " + filePath + " не найден");
        }
    }

    @Override
    public List<Book> importFromCsv(String filePath) throws DataImportException {
        List<Book> books = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNum++;
                if (lineNum == 1) continue; // Пропускаем заголовок

                try {
                    Book book = parseBookFromCsvLine(line);
                    validateBook(book);
                    books.add(book);

                } catch (Exception ex) {
                    String s = "\"Ошибка в строке \" " + lineNum + " " +  ex.getMessage();
                    throw new DataImportException(s);
                }
            }

        } catch (Exception exception) {
            throw new DataImportException("Ошибка чтения файла " + filePath + " " + exception.getMessage());
        }

        return books;
    }


    private Book parseBookFromCsvLine(String line) throws DataValidationException {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (parts.length < 6) {
            throw new DataValidationException("Недостаточно данных в строке");
        }

        try {
            String statusValue = parts[5].replace("\"", "");
            //System.out.println("status value = " + statusValue);
            StatusBook status = null;

            for (StatusBook s : StatusBook.values()) {
                if (s.getValue().equalsIgnoreCase(statusValue)) {
                    status = s;
                    break;
                }
            }

            if (status == null) {
                throw new DataValidationException("Некорректный статус книги: " + statusValue);
            }

            return new Book(
                    Integer.parseInt(parts[0]),
                    parts[1].replace("\"\"", "\""),
                    parts[2].replace("\"\"", "\""),
                    Integer.parseInt(parts[3]),
                    Double.parseDouble(parts[4]),
                    status
            );
        } catch (NumberFormatException e) {
            throw new DataValidationException("Некорректный числовой формат");
        }
    }


    private void validateBook(Book book) throws DataValidationException {
        if (book.getName() == null || book.getName().trim().isEmpty()) {
            throw new DataValidationException("Название книги не может быть пустым");
        }
        if (book.getPrice() <= 0) {
            throw new DataValidationException("Цена должна быть положительной");
        }
        if (book.getYear() < 0) {
            throw new DataValidationException("Некорретный год");
        }
    }
}
