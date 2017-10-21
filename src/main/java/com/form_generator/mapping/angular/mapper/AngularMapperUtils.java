package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.angular.html.InputHtmlElement;

class AngularMapperUtils {
    static HtmlElement mapMatFormFieldElement(FormField formField, String type) {
        HtmlElement matFormField = new HtmlElement("mat-form-field");

        InputHtmlElement input = new InputHtmlElement()
                .addAttribute("type", type)
                .setFieldName(formField.getFieldSingularName())
                .addAttribute("placeholder", formField.getFieldSingularLabel());
        matFormField.appendChild(input);

        return matFormField;
    }
}
