package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 5/3/17.
 */
public class HiddenFormFieldType implements FormFieldType {

    private final static String template = "<input class=\"form-control\" type=\"hidden\" th:name=\"%s\"/>";

    @Override
    public String renderField(FormField formField) {
        return String.format(template,
                formField.getFieldSingularName());
    }

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<HiddenFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }
}
