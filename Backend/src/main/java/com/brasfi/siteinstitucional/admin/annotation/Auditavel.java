package com.brasfi.siteinstitucional.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditavel {

    String acao();

    String entidade();

    boolean capturarIdRetorno() default true;

    boolean incluirParametros() default false;
}