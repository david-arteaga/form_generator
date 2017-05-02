package com.form_generator.field;

import com.form_generator.type.Type;

/**
 * Created by david on 4/29/17.
 */
public interface Field {

    String getFieldSingularName();

    String getFieldPluralName();

    Type getType();

    String getFieldSingularLabel();

    String getFieldPluralLabel();
}
