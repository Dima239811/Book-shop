package dependesies.configurator;

import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaBeanConfigurator implements BeanConfigurator{

    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplementation;

    public JavaBeanConfigurator(String packageToScan, Map<Class, Class> interfaceToImplementation) {
        this.scanner = new Reflections(new ConfigurationBuilder()
                .forPackage(packageToScan)
                .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated));
        this.interfaceToImplementation = new HashMap<>(interfaceToImplementation);
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        // Проверяем, есть ли уже реализация в мапе
        if (interfaceToImplementation.containsKey(interfaceClass)) {
            return (Class<? extends T>) interfaceToImplementation.get(interfaceClass);
        }

        // Если нет - ищем реализацию
        Set<Class<? extends T>> implementations = scanner.getSubTypesOf(interfaceClass);

        if (implementations.isEmpty()) {
            throw new RuntimeException("No implementation found for interface: " + interfaceClass.getName());
        }
        if (implementations.size() > 1) {
            throw new RuntimeException("Multiple implementations found for interface: " + interfaceClass.getName());
        }

        Class<? extends T> implClass = implementations.iterator().next();
        interfaceToImplementation.put(interfaceClass, implClass);
        return implClass;
    }
}
