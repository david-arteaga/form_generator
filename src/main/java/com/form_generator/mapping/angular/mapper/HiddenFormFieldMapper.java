package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.InputHtmlElement;
import com.form_generator.type.HiddenFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class HiddenFormFieldMapper implements FormFieldMapper<HiddenFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, HiddenFormFieldType formFieldType, String formGroupName) {
        return new InputHtmlElement()
                .addAttribute("type", "hidden")
                .setFieldName(formField.getFieldSingularName());
    }

}
