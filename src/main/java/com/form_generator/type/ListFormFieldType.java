package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 4/29/17.
 */
public class ListFormFieldType implements FormFieldType {

    private final FormFieldType listFormFieldType;

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<ListFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }

    public ListFormFieldType(FormFieldType listFormFieldType) {
        this.listFormFieldType = listFormFieldType;
    }

    @Override
    public FormFieldType getListFormFieldType() {
        return listFormFieldType;
    }
}
