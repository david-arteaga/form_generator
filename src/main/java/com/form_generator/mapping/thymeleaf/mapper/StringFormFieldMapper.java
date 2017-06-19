package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.thymeleaf.InputHtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.StringFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class StringFormFieldMapper implements FormFieldMapper<StringFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, StringFormFieldType formFieldType) {
        HtmlElement formGroup = new HtmlElement("div");
        formGroup.addAttribute("class", "form-group");

        InputHtmlElement input = new InputHtmlElement()
                .addAttribute("type", "text")
                .setFieldName(formField.getFieldSingularName())
                .addAttribute("placeholder", formField.getFieldSingularLabel())
                .addAttribute("class", "form-control");
        formGroup.appendChild(input);

        return formGroup;
    }

}
