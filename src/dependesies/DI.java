package dependesies;

import java.util.HashMap;
import java.util.Map;

public class DI {
    private Map<Class, Object> singletonInstances = new HashMap<>();
    private static DI instance;
    private DI() {}

    public <T> void registerBan(Class<T> beanType, T bean) {
        singletonInstances.put(beanType, bean);
    }

    public <T> T getBean(Class<T> beanType) {
        return (T) singletonInstances.get(beanType);
    }

    public synchronized static DI getInstance() {
        if (instance == null) {
            instance = new DI();
        }
        return instance;
    }
}
