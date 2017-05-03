package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Marker annotation to generate a hidden input for fields marked with this annotation
 * Created by david on 5/3/17.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FormHidden {

}
