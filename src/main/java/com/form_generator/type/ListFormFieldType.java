package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

import javax.lang.model.element.TypeElement;

/**
 * Created by david on 4/29/17.
 */
public class ListFormFieldType implements FormFieldType {

        private final FormFieldType listFormFieldType;
    private final TypeElement referencedTypeElement;

    @Override
    public HtmlElement map(FormField formField, Mapping mapping, String formName) {
        FormFieldMapper<ListFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this, formName);
    }

    public ListFormFieldType(FormFieldType listFormFieldType, TypeElement referencedTypeElement) {
        this.listFormFieldType = listFormFieldType;
        this.referencedTypeElement = referencedTypeElement;
    }

    @Override
    public FormFieldType getListFormFieldType() {
        return listFormFieldType;
    }

    public TypeElement getReferencedTypeElement() {
        return referencedTypeElement;
    }
}
