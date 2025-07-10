package util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    String configFileName() default "config.properties";
    String propertyName() default "";
    Class<?> type() default String.class;
}
