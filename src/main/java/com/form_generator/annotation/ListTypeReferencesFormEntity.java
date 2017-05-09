package com.form_generator.annotation;

/**
 * Annotation to specify that an element is meant to represent various instances of a {@link FormEntity}.
 * This is intended to be used on elements that are of a {@link java.util.List} type, in which the type argument to the list is a primitive.
 * The main use case would be for an element in a class used to represent the list of values directly returned from an HTML form, for a given input.
 * Created by david on 5/9/17.
 */
public @interface ListTypeReferencesFormEntity {

    /**
     * The complete name of the class for the {@link com.form_generator.annotation.FormEntity} this field represents
     * @return the class that this element represents
     */
    public String value();

}
