package com.form_generator.mapping.angular;

import com.form_generator.html.FormHtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;
import com.form_generator.mapping.angular.mapper.*;
import com.form_generator.type.*;

/**
 * Created by david on 6/17/17.
 */
public class AngularMapping implements Mapping {
    @Override
    public void completeFormElement(FormHtmlElement formHtmlElement) {
        formHtmlElement
            .addAttribute("class", "form-container")
            .addAttribute("[formGroup]", formHtmlElement.getVariableName())
            .addAttribute("(ngSubmit)", "guardar($event)");
    }

    @Override
    public FormFieldMapper<DateFormFieldType> getMapper(DateFormFieldType fieldType) {
        return new DateFormFieldMapper();
    }

    @Override
    public FormFieldMapper<BooleanFormFieldType> getMapper(BooleanFormFieldType fieldType) {
        return new BooleanFormFieldMapper();
    }

    @Override
    public FormFieldMapper<ListFormFieldType> getMapper(ListFormFieldType fieldType) {
        return new ListFormFieldMapper();
    }

    @Override
    public FormFieldMapper<StringFormFieldType> getMapper(StringFormFieldType fieldType) {
        return new StringFormFieldMapper();
    }

    @Override
    public FormFieldMapper<NumberFormFieldType> getMapper(NumberFormFieldType fieldType) {
        return new NumberFormFieldMapper();
    }

    @Override
    public FormFieldMapper<HiddenFormFieldType> getMapper(HiddenFormFieldType fieldType) {
        return new HiddenFormFieldMapper();
    }

    @Override
    public FormFieldMapper<EntityFormFieldType> getMapper(EntityFormFieldType fieldType) {
        return new EntityFormFieldMapper();
    }


}
