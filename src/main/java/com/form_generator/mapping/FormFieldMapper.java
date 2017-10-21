package com.form_generator.mapping;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.angular.mapper.ListFormFieldMapper;
import com.form_generator.type.FormFieldType;

/**
 * Created by david on 6/17/17.
 */
public interface FormFieldMapper<T extends FormFieldType> {
    /**
     * Map a {@link FormField} to its corresponding {@link HtmlElement}
     * @param formField the form field to map
     * @param formFieldType the type of the form field
     * @param formGroupName used by {@link ListFormFieldMapper}
     * @return
     */
    HtmlElement mapField(FormField formField, T formFieldType, String formGroupName);
}
