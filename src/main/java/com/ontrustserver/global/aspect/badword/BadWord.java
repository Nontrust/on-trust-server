package com.ontrustserver.global.aspect.badword;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BadWord {
    String value() default "";
    boolean parallel() default false;
    String[] targetParam() default {};
}
