package dependesies.config;

import ui.action_factory.ActionFactory;
import ui.action_factory.DefaultActionFactory;

import java.util.HashMap;
import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "src";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementation() {
        // Возвращаем изменяемую Map
        Map<Class, Class> map = new HashMap<>();
        return map;
    }
}
