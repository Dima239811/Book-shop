package clientbookstore.dependesies.factory;

import clientbookstore.controller.DataManager;
import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.dependesies.annotation.Qualifier;
import clientbookstore.dependesies.config.Configuration;
import clientbookstore.dependesies.config.JavaConfiguration;
import clientbookstore.dependesies.configurator.BeanConfigurator;
import clientbookstore.dependesies.configurator.JavaBeanConfigurator;
import clientbookstore.dependesies.context.ApplicationContext;
import lombok.Getter;
import lombok.SneakyThrows;
import clientbookstore.ui.MenuController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        //initBeans();
    }

//    private void initBeans() {
//        DataManager dataManager = getBean(DataManager.class);;
//        Map<Class, Object> beans = new HashMap<>();
//        beans.put(DataManager.class, dataManager);
//        MenuController menuController = new MenuController(dataManager);
//        beans.put(MenuController.class, menuController);
//        applicationContext.setBeanMap(beans);
//    }

    @SneakyThrows
    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> implementationClass;

        if (clazz.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(clazz);
        }
        else {
            implementationClass = clazz;
        }

        T bean =  implementationClass.getDeclaredConstructor().newInstance();

        for (Field field : Arrays.stream(implementationClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toList())) {
            field.setAccessible(true);

            Qualifier qualifier = field.getAnnotation(Qualifier.class);
            Class<?> dependencyType = field.getType();
            Object dependency;

            if (qualifier != null) {
                Class<?> depImpl = beanConfigurator.getImplementationClass(dependencyType, qualifier.value());
                dependency = getBean(depImpl);
            } else {
                if (dependencyType.isInterface()) {
                    Class<?> depImpl = beanConfigurator.getImplementationClass(dependencyType);
                    dependency = getBean(depImpl);
                } else {
                    dependency = getBean(dependencyType);
                }
            }

            field.set(bean, dependency);
        }

        return bean;

    }


}
