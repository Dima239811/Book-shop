package clientbookstore.dependesies.config;

import clientbookstore.service.csv.BookCsvService;
import clientbookstore.service.csv.CustomerCsvService;
import clientbookstore.service.csv.OrderCsvService;
import clientbookstore.service.csv.RequestBookCsvService;

import clientbookstore.ui.action_factory.DefaultActionFactory;

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
                Map.entry("requestBookCsvService", RequestBookCsvService.class),
                Map.entry("actionFactory", DefaultActionFactory.class)
        );
    }
}
