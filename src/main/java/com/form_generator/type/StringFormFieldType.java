package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 4/29/17.
 */
public class StringFormFieldType implements FormFieldType {

    @Override
    public HtmlElement map(FormField formField, Mapping mapping, String formName) {
        FormFieldMapper<StringFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this, formName);
    }

}
