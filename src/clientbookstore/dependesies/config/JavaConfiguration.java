package clientbookstore.dependesies.config;

import clientbookstore.csv.BookCsvService;
import clientbookstore.csv.CustomerCsvService;
import clientbookstore.csv.OrderCsvService;
import clientbookstore.csv.RequestBookCsvService;

import clientbookstore.csv.*;

import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "clientbookstore";
    }

    @Override
    public Map<String, Class> getInterfaceToImplementation() {
        return Map.ofEntries(
                Map.entry("bookCsvService", BookCsvService.class),
                Map.entry("orderCsvService", OrderCsvService.class),
                Map.entry("customerCsvService", CustomerCsvService.class),
                Map.entry("requestBookCsvService", RequestBookCsvService.class)
        );
    }
}
