package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.InputHtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.BooleanFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class BooleanFormFieldMapper implements FormFieldMapper<BooleanFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, BooleanFormFieldType formFieldType) {
        HtmlElement div = new HtmlElement("div");
        div.addAttribute("class", "form-group");

        HtmlElement checkboxDiv = new HtmlElement("div");
        checkboxDiv.addAttribute("class", "checkbox");
        div.appendChild(checkboxDiv);

        HtmlElement label = new HtmlElement("label");
        label.addAttribute("class", "control-label");
        checkboxDiv.appendChild(label);

        HtmlElement input = new InputHtmlElement()
                .setFieldName(formField.getFieldSingularName())
                .addAttribute("type", "checkbox");

        label.appendChild(input);
        label.appendChild(new TextNode(formField.getFieldSingularLabel()));

        return div;
    }

}
