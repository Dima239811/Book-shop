package clientbookstore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppConfig {
    private static int staleBookMonths;
    private static boolean autoCloseRequestsEnabled;

    @Value("${book.stale.months:3}")
    private int staleBookMonthsField;

    @Value("${book.auto.close.requests:true}")
    private boolean autoCloseRequestsEnabledField;

    @PostConstruct
    public void init() {
        staleBookMonths = staleBookMonthsField;
        autoCloseRequestsEnabled = autoCloseRequestsEnabledField;
    }

    public static int getStaleBookMonths() {
        return staleBookMonths;
    }

    public static boolean isAutoCloseRequestsEnabled() {
        return autoCloseRequestsEnabled;
    }
}
