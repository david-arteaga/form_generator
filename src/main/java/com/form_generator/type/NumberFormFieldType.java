package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 4/29/17.
 */
public class NumberFormFieldType implements FormFieldType {

    private final static String template =
            "<div class=\"form-group\">\n" +
            "    <input " +
                    "type=\"number\" " +
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

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<NumberFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }
}
