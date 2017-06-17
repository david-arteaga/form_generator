package com.form_generator.form.field;

import com.form_generator.type.FormFieldType;

/**
 * Created by david on 4/29/17.
 */
public interface FormField {

    String getFieldSingularName();

    String getFieldPluralName();

    FormFieldType getFormFieldType();

    String getFieldSingularLabel();
}
