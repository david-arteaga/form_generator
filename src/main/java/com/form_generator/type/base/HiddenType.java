package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.Type;

/**
 * Created by david on 5/3/17.
 */
public class HiddenType implements Type {

    private final static String template = "<input class=\"form-control\" type=\"hidden\" th:name=\"%s\"/>";

    @Override
    public String renderField(Field field) {
        return String.format(template,
                field.getFieldSingularName());
    }
}
