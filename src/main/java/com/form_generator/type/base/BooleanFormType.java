package com.form_generator.type.base;

import com.form_generator.field.FormField;
import com.form_generator.type.FormType;

/**
 * Created by david on 5/1/17.
 */
public class BooleanFormType implements FormType {

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
    public String renderField(FormField formField) {
        return String.format(template,
                formField.getFieldSingularName(),
                formField.getFieldSingularLabel());
    }
}
