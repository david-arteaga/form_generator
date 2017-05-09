package com.form_generator.annotation;

import java.lang.annotation.*;

/**
 * Annotation to specify the name for the 'name' and 'id' fields of a type used on a field that has not been declared by the user.
 * If this annotation is placed on an element with a type whose declaration is already annotated with a {@link FormEntity} annotation,
 * the values in this annotation take precedence.
 * If the element is also annotated with a {@link ReferencesFormEntity} annotation, the values in this annotations take precedence.
 * This annotation also works for types used ar arguments to {@link java.util.List} types.
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
    String idFieldName();

    /**
     *
     * @return the name of the field that represents an entity's name
     */
    String nameFieldName();
}
