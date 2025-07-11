package dependesies.factory;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import dependesies.annotation.Inject;
import dependesies.config.Configuration;
import dependesies.config.JavaConfiguration;
import dependesies.configurator.BeanConfigurator;
import dependesies.configurator.JavaBeanConfigurator;
import dependesies.context.ApplicationContext;
import lombok.Getter;
import lombok.SneakyThrows;

public class BeanFactory {
    //private static final BeanFactory BEAN_FACTORY = new BeanFactory();
    @Getter
    private final BeanConfigurator beanConfigurator;

    private final Configuration configuration;
    private ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext) {
        this.configuration = new JavaConfiguration();
        beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan()
        , configuration.getInterfaceToImplementation());
        this.applicationContext = applicationContext;
    }


    @SneakyThrows
    public <T> T getBean(Class<T> beanType) {
        Class<? extends T> implementationClass = beanType;
        if (implementationClass.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);
        }
        T bean = null;
        try {
            bean = implementationClass.getDeclaredConstructor().newInstance();
            for (Field field : Arrays.stream(implementationClass.getDeclaredFields()).
                    filter(field -> field.isAnnotationPresent(Inject.class))
                    .collect(Collectors.toList())) {
                field.setAccessible(true);
                field.set(bean, applicationContext.getBean(field.getType()));
            }


        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("No default constructor in " + implementationClass.getName(), e);
        }

        return bean;
    }
}
