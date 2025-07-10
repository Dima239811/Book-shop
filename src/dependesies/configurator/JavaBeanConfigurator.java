package dependesies.configurator;

import org.reflections.Reflections;

import java.util.Set;

public class JavaBeanConfigurator implements BeanConfigurator{

    final Reflections scanner;

    public JavaBeanConfigurator(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        Set<Class<? extends T>> implementation = scanner.getSubTypesOf(interfaceClass);

        if (implementation.size() != 1) {
            throw new RuntimeException("Interface has 0 or more than 1 realisations");
        }

        return implementation.stream().findFirst().get();
    }
}
