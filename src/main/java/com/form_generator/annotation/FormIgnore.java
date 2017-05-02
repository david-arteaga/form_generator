package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Marker annotation to not generate an input for fields marked with this annotation
 * Created by david on 5/2/17.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FormIgnore {
}
