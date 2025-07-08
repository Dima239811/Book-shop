package csv;

import java.util.List;

public interface CsvService<T> {
    void exportToCsv(List<T> items, String filePath) throws Exception;
    List<T> importFromCsv(String filePath) throws Exception;
}
