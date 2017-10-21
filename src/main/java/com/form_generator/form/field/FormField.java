package com.form_generator.form.field;

import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.Mapping;
import com.form_generator.type.FormFieldType;

/**
 * Created by david on 4/29/17.
 */
public interface FormField {

    String getFieldSingularName();

    String getFieldPluralName();

    FormFieldType getFormFieldType();

    String getFieldSingularLabel();

    String getFieldPluralLabel();

    HtmlElement map(Mapping mapping, String formName);
}
