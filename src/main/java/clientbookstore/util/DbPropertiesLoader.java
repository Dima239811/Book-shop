package clientbookstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertiesLoader {
    private static final String PROPERTIES_FILE = "db.properties";

    public static Properties load() {
        Properties props = new Properties();

        try (InputStream input = DbPropertiesLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти файл " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке " + PROPERTIES_FILE, e);
        }

        return props;
    }
}
