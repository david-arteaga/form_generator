package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.thymeleaf.html.InputHtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.DateFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class DateFormFieldMapper implements FormFieldMapper<DateFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, DateFormFieldType formFieldType) {
        HtmlElement formGroup = new HtmlElement("div")
                .addAttribute("class", "form-group");

        InputHtmlElement input = new InputHtmlElement()
                .addAttribute("type", formFieldType.getDateInputType().getName())
                .setFieldName(formField.getFieldSingularName())
                .addAttribute("class", "form-control")
                .addAttribute("placeholder", formField.getFieldSingularLabel());
        formGroup.appendChild(input);

        return formGroup;
    }

}
