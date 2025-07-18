package dependesies.config;

import java.util.Map;

public interface Configuration {
    String getPackageToScan();

    Map<String, Class> getInterfaceToImplementation();
}
