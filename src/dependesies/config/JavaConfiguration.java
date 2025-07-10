package dependesies.config;

import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "src";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementation() {
        return Map.of();
    }
}
