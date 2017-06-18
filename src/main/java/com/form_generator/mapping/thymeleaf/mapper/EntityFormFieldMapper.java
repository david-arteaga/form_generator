package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.EntityFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class EntityFormFieldMapper implements FormFieldMapper<EntityFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, EntityFormFieldType formFieldType) {
        throw new UnsupportedOperationException();
    }


}
