package com.jspbbs.core.annotation;

import com.jspbbs.core.aop.AopAction;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
    Class<AopAction> value();
}
