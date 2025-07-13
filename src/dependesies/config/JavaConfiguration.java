package dependesies.config;

import service.IService;
import service.OrderService;

import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "src";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementation() {
        return Map.of(IService.class, OrderService.class);
    }
}
