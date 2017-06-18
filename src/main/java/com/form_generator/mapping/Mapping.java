package com.form_generator.mapping;

import com.form_generator.html.FormHtmlElement;
import com.form_generator.type.*;

/**
 * Created by david on 6/17/17.
 */
public interface Mapping {

    // add any attributes necessary to the form tag
    void completeFormElement(FormHtmlElement formHtmlElement);

    FormFieldMapper<DateFormFieldType> getMapper(DateFormFieldType fieldType);
    FormFieldMapper<BooleanFormFieldType> getMapper(BooleanFormFieldType fieldType);
    FormFieldMapper<ListFormFieldType> getMapper(ListFormFieldType fieldType);
    FormFieldMapper<StringFormFieldType> getMapper(StringFormFieldType fieldType);
    FormFieldMapper<NumberFormFieldType> getMapper(NumberFormFieldType fieldType);
    FormFieldMapper<HiddenFormFieldType> getMapper(HiddenFormFieldType fieldType);
    FormFieldMapper<EntityFormFieldType> getMapper(EntityFormFieldType fieldType);

}
