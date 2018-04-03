package org.sagittarius.common.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JasonZhang 2018 - 04 - 03 - 14:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpRequest {

    HttpMethodEnum method();

    String url();

    String param() default "";

    String header() default "";

//    int code() default 200;

//    String expect();
}
