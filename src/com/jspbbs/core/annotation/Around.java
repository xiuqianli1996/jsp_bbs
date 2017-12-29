package com.jspbbs.core.annotation;

import com.jspbbs.core.aop.AopAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Around {
    Class<? extends AopAction> value();
}
