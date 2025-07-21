package clientbookstore.model.converters;

import clientbookstore.model.enums.StatusBook;
import jakarta.persistence.AttributeConverter;

public class StatusBookConverter implements AttributeConverter<StatusBook, String> {
    @Override
    public String convertToDatabaseColumn(StatusBook statusBook) {
        String value = statusBook != null ? statusBook.getValue() : null;
        System.out.println("Convert to DB: " + statusBook + " -> " + value);
        return value;
    }

    @Override
    public StatusBook convertToEntityAttribute(String dbData) {
        System.out.println("Convert from DB: " + dbData);
        if (dbData == null) {
            return null;
        }
        // Убедитесь, что trim() убирает лишние пробелы
        String trimmedValue = dbData.trim();
        System.out.println("After trim: '" + trimmedValue + "'");
        return StatusBook.fromValue(trimmedValue);
    }
}
