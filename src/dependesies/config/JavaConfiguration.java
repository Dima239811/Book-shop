package dependesies.config;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "src";
    }
}
