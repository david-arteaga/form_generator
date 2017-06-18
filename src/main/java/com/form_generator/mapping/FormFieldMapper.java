package com.form_generator.mapping;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.type.FormFieldType;

/**
 * Created by david on 6/17/17.
 */
public interface FormFieldMapper<T extends FormFieldType> {
    HtmlElement mapField(FormField formField, T formFieldType);
}
