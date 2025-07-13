package dependesies.configurator;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JavaBeanConfigurator implements BeanConfigurator{
    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplementation;

    public JavaBeanConfigurator(String packageToScan, Map<Class, Class> interfaceToImplementation) {
        this.scanner = new Reflections(packageToScan);
        this.interfaceToImplementation = new ConcurrentHashMap<>(interfaceToImplementation);
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {

        return interfaceToImplementation.computeIfAbsent(interfaceClass, clazz -> {
            Set<Class <? extends T>> implementation = scanner.getSubTypesOf(interfaceClass);

            if (implementation.size() != 0) {
                throw new RuntimeException("Interface has 0 or more than 1 implementations");
            }
            return implementation.stream().findFirst().get();
        });


    }
}
