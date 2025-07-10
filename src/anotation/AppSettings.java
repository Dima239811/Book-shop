package anotation;

public class AppSettings {
    private static AppSettings instance;
    @ConfigProperty(propertyName = "book.stale.months", type = int.class)
    private int staleBookMonths;

    @ConfigProperty(propertyName = "book.auto.close.requests", type = boolean.class)
    private boolean autoCloseRequests;

    private AppSettings() {
        ConfigLoader.configure(this);
    }

    public static synchronized AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
        }
        return instance;
    }

    public int getStaleBookMonths() {
        return staleBookMonths;
    }

    public boolean isAutoCloseRequestsEnabled() {
        return autoCloseRequests;
    }
}
