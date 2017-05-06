package com.form_generator.field;

import com.form_generator.type.FormType;

/**
 * Created by david on 4/29/17.
 */
public interface Field {

    String getFieldSingularName();

    String getFieldPluralName();

    FormType getFormType();

    String getFieldSingularLabel();

    String getFieldPluralLabel();
}
