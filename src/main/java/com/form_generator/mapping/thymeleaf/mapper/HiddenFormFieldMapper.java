package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.thymeleaf.InputHtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.HiddenFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class HiddenFormFieldMapper implements FormFieldMapper<HiddenFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, HiddenFormFieldType formFieldType) {
        return new InputHtmlElement()
                .addAttribute("class", "form-control")
                .addAttribute("type", "hidden")
                .setFieldName(formField.getFieldSingularName());
    }

    private final static String template = "<input class=\"form-control\" type=\"hidden\" th:name=\"%s\"/>";

    public String renderField(FormField formField) {
        return String.format(template,
                formField.getFieldSingularName());
    }

}
