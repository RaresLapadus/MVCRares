package ro.teamnet.zth.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rares on 5/6/2015.
 */
@Target({ElementType.METHOD})
@Retention(RUNTIME)
@Documented
public @interface MyRequestMethod{
   String urlPath();
   String methodType() default "GET";

}
