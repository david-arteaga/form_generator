package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.Type;

/**
 * Created by david on 4/29/17.
 */
public class StringType implements Type {

    private final static String template =
            "<div class=\"form-group\">\n" +
            "    <input " +
                    "type=\"text\" " +
                    "name=\"%s\" " +
                    "placeholder=\"%s\" " +
                    "class=\"form-control\" " +
                "/>\n" +
            "</div>";

    @Override
    public String renderField(Field field) {
        return String.format(template,
                field.getFieldSingularName(), field.getFieldSingularLabel());
    }
}
