package com.form_generator.mapping.angular.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.Field;
import com.form_generator.mapping.angular.html.InputHtmlElement;
import com.form_generator.type.BooleanFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class BooleanFormFieldMapper implements FormFieldMapper<BooleanFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, BooleanFormFieldType formFieldType, String formGroupName) {
        return new HtmlElement("mat-checkbox")
                .addAttribute(Field.FIELD_ATTRIBUTE_NAME, formField.getFieldSingularName())
                .appendChild(new TextNode(formField.getFieldSingularLabel()));
    }

}
