package dependesies.configurator;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaBeanConfigurator implements BeanConfigurator{

    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplementation;

    public JavaBeanConfigurator(String packageToScan, Map<Class, Class> interfaceToImplementation) {
        this.scanner = new Reflections(packageToScan);
        this.interfaceToImplementation = interfaceToImplementation;
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {

        return interfaceToImplementation.computeIfAbsent(interfaceClass, clazz -> {
            Set<Class<? extends T>> implementation = scanner.getSubTypesOf(interfaceClass);

            if (implementation.size() != 1) {
                throw new RuntimeException("Interface has 0 or more than 1 realisations");
            }

            return implementation.stream().findFirst().get();
        });

    }
}
