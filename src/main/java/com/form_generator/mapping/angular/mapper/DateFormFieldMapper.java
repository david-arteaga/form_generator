package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.InputHtmlElement;
import com.form_generator.type.DateFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class DateFormFieldMapper implements FormFieldMapper<DateFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, DateFormFieldType formFieldType, String formGroupName) {
        return AngularMapperUtils.mapMatFormFieldElement(formField, formFieldType.getDateInputType().getName());
    }

}
