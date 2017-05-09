package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Annotation to specify that an element is meant to represent a {@link FormEntity}.
 * This is intended to be used on elements that are of a primitive type, which represent the id used for an entity.
 * The main use case would be for an element in a class used to represent the value directly returned from an HTML form.
 * When processing this annotation, the actual element type will not be considered.
 * Created by david on 5/8/17.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ReferencesFormEntity {

    /**
     * The complete name of the class for the {@link com.form_generator.annotation.FormEntity} this field represents
     * @return the class that this element represents
     */
    public Class<?> value();

}
