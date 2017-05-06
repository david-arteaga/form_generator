package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Annotation to specify the name for the 'name' and 'id' fields of a type used on a field that has not been declared by the user
 * Created by david on 5/2/17.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface PredefinedType {
    /**
     *
     * @return the name of the field that represents an entity's id
     */
    String idFieldName() default "id";

    /**
     *
     * @return the name of the field that represents an entity's name
     */
    String nameFieldName() default "name";
}
