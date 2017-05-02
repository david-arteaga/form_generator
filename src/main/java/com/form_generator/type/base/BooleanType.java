package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.Type;

/**
 * Created by david on 5/1/17.
 */
public class BooleanType implements Type {

    private static final String template =
            "<div class=\"form-group\">\n" +
                    "    <div class=\"checkbox\">\n" +
                    "        <label class=\"control-label\">\n" +
                    "            <input type=\"checkbox\" name=\"%s\" />" +
                    "%s\n" +
                    "</label>\n" +
                    "    </div>\n" +
                    "</div>";

    @Override
    public String renderField(Field field) {
        return String.format(template,
                field.getFieldSingularName(),
                field.getFieldSingularLabel());
    }
}
