package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Indicates the name for the fields representing an entity's id and name
 * Created by david on 4/30/17.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface FormEntity {

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

    /**
     * Specify if a form will be generated for this entity
     * @return whether a form will be generated for this entity
     */
    boolean generateForm() default true;
}

