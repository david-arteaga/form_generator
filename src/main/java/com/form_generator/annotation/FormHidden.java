package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Marker annotation to generate a hidden input for elements marked with this annotation
 * All elements marked with this annotation will have an input of type 'hidden' generated for them,
 * regardless of other annotations present on the element.
 * Created by david on 5/3/17.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FormHidden {

}
