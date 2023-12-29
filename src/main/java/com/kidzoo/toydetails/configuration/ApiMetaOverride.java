package com.kidzoo.toydetails.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMetaOverride {
  /**
   * The parameter type of the parameter.
   * <p>
   * Valid values are {@code path}, {@code query}, {@code body},
   * {@code header} or {@code form}.
   */
  String paramType() default "";
}