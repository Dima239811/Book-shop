package dependesies.factory;

import dependesies.annotation.Inject;
import dependesies.config.Configuration;
import dependesies.config.JavaConfiguration;
import dependesies.configurator.BeanConfigurator;
import dependesies.configurator.JavaBeanConfigurator;
import dependesies.context.ApplicationContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanFactory {

    private final Configuration configuration;
    @Getter
    private final BeanConfigurator beanConfigurator;
    private ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext) {
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementation());
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> implementationClass = clazz;

        if (implementationClass.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);

        }

        T bean =  implementationClass.getDeclaredConstructor().newInstance();

        for (Field field : Arrays.stream(implementationClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toList())) {
            field.setAccessible(true);
            field.set(bean, applicationContext.getBean(field.getType()));
        }

        return bean;

    }


}
