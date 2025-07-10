package dependesies.factory;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;

import dependesies.config.Configuration;
import dependesies.config.JavaConfiguration;
import dependesies.configurator.BeanConfigurator;
import dependesies.configurator.JavaBeanConfigurator;
import lombok.SneakyThrows;

public class BeanFactory {
    private static final BeanFactory BEAN_FACTORY = new BeanFactory();
    private final BeanConfigurator beanConfigurator;
    private final Configuration configuration;

    private BeanFactory() {
        this.configuration = new JavaConfiguration();
        beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan());
    }

    public static BeanFactory getInstance() {
        return BEAN_FACTORY;
    }

    @SneakyThrows
    public <T> T getBean(Class<T> beanType) {
        Class<? extends T> implementationClass = beanType;
        if (implementationClass.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);
        }

        try {
            return implementationClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("No default constructor in " + implementationClass.getName(), e);
        }
    }
}
