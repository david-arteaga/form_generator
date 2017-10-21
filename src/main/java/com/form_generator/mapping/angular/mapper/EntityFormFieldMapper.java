package com.form_generator.mapping.angular.mapper;

import com.form_generator.entity.Entity;
import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.SelectHtmlElement;
import com.form_generator.type.EntityFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class EntityFormFieldMapper implements FormFieldMapper<EntityFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, EntityFormFieldType formFieldType, String formGroupName) {
        Entity entity = formFieldType.getEntity();

        return new HtmlElement("input-autocomplete")
                .addAttribute("[formGroup]", formGroupName)
                .addAttribute("controlName", formField.getFieldSingularLabel())
                .addAttribute("displayKey", entity.getNameFieldName())
                .addAttribute("[options]", formField.getFieldSingularName() + "Options");
    }

}