package util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class ConfigLoader {
    public static void configure(Object target) {
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);

                String configFileName = annotation.configFileName();
                String propertyName = annotation.propertyName().isEmpty()
                        ? clazz.getSimpleName() + "." + field.getName()
                        : annotation.propertyName();

                try (InputStream input = clazz.getClassLoader().getResourceAsStream(configFileName)) {
                    if (input == null) {
                        System.out.printf("Конфигурационный файл %s не найден.%n", configFileName);
                        continue;
                    }

                    Properties props = new Properties();
                    props.load(input);
                    String value = props.getProperty(propertyName);

                    if (value != null) {
                        field.setAccessible(true);
                        Object parsedValue = parseValue(value, field.getType());
                        field.set(target, parsedValue);
                    } else {
                        System.out.printf("Свойство %s не найдено в файле %s%n", propertyName, configFileName);
                    }
                } catch (Exception e) {
                    System.out.printf("Ошибка при конфигурации поля %s: %s%n", field.getName(), e.getMessage());
                }
            }
        }
    }

    private static Object parseValue(String value, Class<?> targetType) {
        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else {
            return value;
        }
    }
}
