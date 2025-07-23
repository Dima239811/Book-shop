package clientbookstore;

import clientbookstore.controller.MainContr;
import clientbookstore.ui.Builder;
import clientbookstore.ui.MenuController;
import clientbookstore.util.DbPropertiesLoader;
import clientbookstore.util.SpringConfig;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties props = DbPropertiesLoader.load();

        runFlywayMigrations(props);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        MainContr dataManager = context.getBean(MainContr.class);
        Builder builder = context.getBean(Builder.class);
        MenuController menuController = context.getBean(MenuController.class);

        builder.buildMenu();
        menuController.run();

        context.registerShutdownHook();
    }

    private static void runFlywayMigrations(Properties props) {
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .connectRetries(10)
                .mixed(true)
                .group(true)
                .load();

        flyway.migrate();
    }
}
