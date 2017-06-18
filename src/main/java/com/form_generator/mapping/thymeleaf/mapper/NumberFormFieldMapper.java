package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.NumberFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class NumberFormFieldMapper implements FormFieldMapper<NumberFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, NumberFormFieldType formFieldType) {
        throw new UnsupportedOperationException();
    }


}
