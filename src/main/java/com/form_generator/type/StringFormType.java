package com.form_generator.type;

import com.form_generator.field.FormField;
import com.form_generator.type.FormType;

/**
 * Created by david on 4/29/17.
 */
public class StringFormType implements FormType {

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
    public String renderField(FormField formField) {
        return String.format(template,
                formField.getFieldSingularName(), formField.getFieldSingularLabel());
    }
}
