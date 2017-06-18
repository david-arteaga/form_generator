package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 5/1/17.
 */
public class BooleanFormFieldType implements FormFieldType {

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

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<BooleanFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }
}