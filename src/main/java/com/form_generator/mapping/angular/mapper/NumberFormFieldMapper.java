package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.InputHtmlElement;
import com.form_generator.type.NumberFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class NumberFormFieldMapper implements FormFieldMapper<NumberFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, NumberFormFieldType formFieldType, String formGroupName) {
        return AngularMapperUtils.mapMatFormFieldElement(formField, "number");
    }

}
