package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.InputHtmlElement;
import com.form_generator.type.StringFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class StringFormFieldMapper implements FormFieldMapper<StringFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, StringFormFieldType formFieldType, String formGroupName) {
        return AngularMapperUtils.mapMatFormFieldElement(formField, "text");
    }

}
