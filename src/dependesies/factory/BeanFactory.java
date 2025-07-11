package dependesies.factory;

import dependesies.annotation.Inject;
import dependesies.config.Configuration;
import dependesies.config.JavaConfiguration;
import dependesies.configurator.BeanConfigurator;
import dependesies.configurator.JavaBeanConfigurator;
import dependesies.context.ApplicationContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanFactory {
    @Getter
    private final BeanConfigurator beanConfigurator;
    private final Configuration configuration;
    private final ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext) {
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(
                new JavaConfiguration().getPackageToScan(),
                new JavaConfiguration().getInterfaceToImplementation()
        );
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    public <T> T getBean(Class<T> beanType) {
        Class<? extends T> implementationClass = beanType.isInterface()
                ? beanConfigurator.getImplementationClass(beanType)
                : beanType;

        // Поиск подходящего конструктора
        Constructor<?> constructor = findSuitableConstructor(implementationClass);

        // Создание аргументов для конструктора
        Object[] args = Arrays.stream(constructor.getParameterTypes())
                .map(applicationContext::getBean)
                .toArray();

        // Создание экземпляра
        T instance = (T) constructor.newInstance(args);

        // Внедрение зависимостей в поля
        injectFields(instance);

        return instance;
    }

    private Constructor<?> findSuitableConstructor(Class<?> clazz) throws NoSuchMethodException {
        // Ищем конструктор с @Inject
        Constructor<?> injectConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElse(null);

        return injectConstructor != null ? injectConstructor : clazz.getDeclaredConstructor();
    }

    private void injectFields(Object instance) throws IllegalAccessException {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                field.set(instance, applicationContext.getBean(field.getType()));
            }
        }
    }
}