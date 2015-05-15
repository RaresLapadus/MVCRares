package ro.teamnet.zth.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rares on 5/7/2015.
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface MyRequestParam {
    String paramName();
}
